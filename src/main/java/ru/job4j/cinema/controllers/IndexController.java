package ru.job4j.cinema.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.services.FilmShowsService;

import javax.servlet.http.HttpSession;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 10/05/2022 - 23:18
 */
@ThreadSafe
@Controller
public class IndexController {

    private final FilmShowsService service;

    public IndexController(FilmShowsService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session, @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("filmShows", service.findAllShows());
        model.addAttribute("fail", fail != null);
        return "index";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Guest");
        }
        return user;
    }
}
