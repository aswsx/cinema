package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.models.FilmShow;
import ru.job4j.cinema.models.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 17/05/2022 - 16:16
 */
@Repository
public class TicketsDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(TicketsDBStore.class);
    private final BasicDataSource pool;

    public TicketsDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO tickets(show_id, row, seat, user_id) " +
                     "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getShowId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getSeat());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt("id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Ticket Add Error", e);
        }
        return Optional.ofNullable(ticket);
    }

    public Collection<Ticket> findTicketsByShowId(int showId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM tickets WHERE show_id = ? ORDER BY id")
        ) {
            createQuery(showId, tickets, ps);
        } catch (Exception e) {
            LOG.error("Find Tickets By Show Id Error", e);
        }
        return tickets;
    }

    public Collection<Ticket> findTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT t.id, t.row, t.seat, t.user_id, s.name AS show_name " +
                     "FROM tickets AS t JOIN filmshows s ON s.id = t.show_id WHERE user_id = ? ORDER BY t.id")) {
            createQuery(userId, tickets, ps);
        } catch (Exception e) {
            LOG.error("Find Tickets By User Id Error", e);
        }
        return tickets;
    }

    private void createQuery(int id, List<Ticket> tickets, PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
        try (ResultSet it = ps.executeQuery()) {
            Ticket ticket;
            while (it.next()) {
                ticket = new Ticket(
                        it.getInt("row"),
                        it.getInt("seat"),
                        new FilmShow(it.getString("show_name")),
                        it.getInt("user_id"));
                ticket.setId(it.getInt("id"));
                tickets.add(ticket);
            }
        }
    }
}
