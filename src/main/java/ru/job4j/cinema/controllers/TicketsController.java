package ru.job4j.cinema.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.models.Hall;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.services.TicketsService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 10/05/2022 - 23:18
 */
@ThreadSafe
@Controller
public class TicketsController {
    private final TicketsService service;

    private final Hall hall = new Hall();

    private static final String GUEST = "Guest";

    public TicketsController(TicketsService service) {
        this.service = service;
    }

    @PostMapping("/selectSeat")
    public String seatSelect(Model model, @ModelAttribute Ticket ticket, HttpSession session,
                             @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = getUser(session);
        model.addAttribute("ticket", ticket);
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        model.addAttribute("rows", getAvailableRows(ticket.getShowId()));
        model.addAttribute("seats", getAvailableSeats(ticket
                .getShowId(), ticket.getRow()));
        return "cinema/selectSeat";
    }

    @PostMapping("/addTicket")
    public String addTicket(Model model, @ModelAttribute Ticket ticket, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        Optional<Ticket> addedTicket = service.addTicket(ticket);
        if (addedTicket.isPresent()) {
            return "/cinema/successBooking";
        }
        return "redirect:/index?fail=true";
    }

    @GetMapping("/cabinet")
    public String findTicketsByUserId(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("tickets", service.findTicketsByUserId(user.getUserId()));
        return "/cinema/cabinet";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername(GUEST);
        }
        return user;
    }

    public List<Integer> getAvailableRows(int showId) {
        Collection<Ticket> tickets = service.findTicketsByShowId(showId);
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
        Collection<Ticket> tickets = service.findTicketsByShowId(showId);
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
}
