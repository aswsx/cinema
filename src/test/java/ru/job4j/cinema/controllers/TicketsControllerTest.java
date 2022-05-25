package ru.job4j.cinema.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.models.FilmShow;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.services.TicketsService;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 24/05/2022 - 21:48
 */
class TicketsControllerTest {

    @Test
    void addTicketTest() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TicketsService ticketsService = mock(TicketsService.class);
        TicketsController controller = new TicketsController(ticketsService);
        FilmShow show = new FilmShow(1, "Avengers");
        Ticket ticket = new Ticket(1, 1, 1, 1, 1, show);
        when(ticketsService.addTicket(ticket)).thenReturn(ticket);
        String page = controller.addTicket(model, ticket, session);
        verify(ticketsService).addTicket(ticket);
        assertThat(page, is("/cinema/successBooking"));
    }

    @Test
    void findTicketsByUserIdTest() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TicketsService ticketsService = mock(TicketsService.class);
        TicketsController controller = new TicketsController(ticketsService);
        FilmShow show = new FilmShow(1, "Avengers");
        Ticket ticket = new Ticket(1, 1, 1, 1, 1, show);
        when(ticketsService.addTicket(ticket)).thenReturn(ticket);
        controller.addTicket(model, ticket, session);
        String page = controller.findTicketsByUserId(model, session);
        assertThat(page, is("/cinema/cabinet"));
    }
}