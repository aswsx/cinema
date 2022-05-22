package ru.job4j.cinema.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.models.Ticket;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.services.TicketsService;

import javax.servlet.http.HttpSession;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 10/05/2022 - 23:18
 */
@ThreadSafe
@Controller
public class TicketsController {
    private final TicketsService service;

    private static final String GUEST = "Guest";

    public TicketsController(TicketsService service) {
        this.service = service;
    }

    @PostMapping("/selectSeat")
    public String seatSelect(Model model, @ModelAttribute Ticket ticket, HttpSession session,
                             @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername(GUEST);
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        model.addAttribute("rows", service.getAvailableRows(ticket.getShowId()));
        model.addAttribute("seats", service
                .getAvailableSeats(ticket.getShowId(), ticket.getRow()));
        return "cinema/selectSeat";
    }

    @PostMapping("/addTicket")
    public String addTicket(@ModelAttribute Ticket ticket, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername(GUEST);
        }
        model.addAttribute("user", user);
        Ticket addedTicket = service.addTicket(ticket);
        if (addedTicket.getId() != 0) {
            return "/cinema/successBooking";
        }
        return "redirect:/index?fail=true";
    }

    @GetMapping("/cabinet")
    public String addTicket(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername(GUEST);
        }
        model.addAttribute("user", user);
        model.addAttribute("tickets", service.findTicketsByUserId(user.getUserId()));
        return "/cinema/cabinet";
    }

    @GetMapping("/addTicket")
    public String finish() {
        return "/cinema/finish";
    }
}
