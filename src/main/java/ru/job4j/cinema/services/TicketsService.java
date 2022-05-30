package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.persistence.TicketsDBStore;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 17/05/2022 - 0:02
 */
@Service
@ThreadSafe
public class TicketsService {
    private final TicketsDBStore store;

    public TicketsService(TicketsDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return store.addTicket(ticket);
    }

    public Collection<Ticket> findTicketsByUserId(int userId) {
        return store.findTicketsByUserId(userId);
    }

    public Collection<Ticket> findTicketsByShowId(int showId) {
        return store.findTicketsByShowId(showId);
    }
}
