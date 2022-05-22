package ru.job4j.cinema.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.services.FilmShowsService;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 11/05/2022 - 21:59
 */
@ThreadSafe
@Controller
public class FilmShowsController {
    private final FilmShowsService service;

    public FilmShowsController(FilmShowsService service) {
        this.service = service;
    }

    @GetMapping("/shows")
    public String showSelect(Model model) {
        model.addAttribute("shows", service.findAllShows());
        return "cinema/shows";
    }
}