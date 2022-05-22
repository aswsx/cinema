package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.models.Hall;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.persistence.TicketsDBStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 17/05/2022 - 0:02
 */
@Service
@ThreadSafe
public class TicketsService {
    private final TicketsDBStore store;
    private final Hall hall = new Hall();

    public TicketsService(TicketsDBStore store) {
        this.store = store;
    }

    public Ticket addTicket(Ticket ticket) {
        return store.addTicket(ticket);
    }

    public List<Integer> getAvailableRows(int showId) {
        Collection<Ticket> tickets = store.findTicketsByShowId(showId);
        List<Integer> availableRows = new ArrayList<>();
        for (int i = 1; i <= hall.getRows(); i++) {
            availableRows.add(i);
        }
        for (Ticket ticket : tickets) {
            if (getAvailableSeats(showId, ticket.getRow()).isEmpty()) {
                availableRows.remove(Integer.valueOf(ticket.getRow()));
            }
        }
        return availableRows;
    }

    public List<Integer> getAvailableSeats(int showId, int row) {
        Collection<Ticket> tickets = store.findTicketsByShowId(showId);
        List<Integer> availableSeats = new ArrayList<>();
        for (int i = 1; i <= hall.getSeats(); i++) {
            availableSeats.add(i);
        }
        for (Ticket ticket : tickets) {
            if (ticket.getRow() == row) {
                availableSeats.remove(Integer.valueOf(ticket.getSeat()));
            }
        }
        return availableSeats;
    }

    public Collection<Ticket> findTicketsByUserId(int userId) {
        return store.findTicketsByUserId(userId);
    }
}
