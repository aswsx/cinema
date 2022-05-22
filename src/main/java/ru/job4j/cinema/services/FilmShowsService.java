package ru.job4j.cinema.services;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.models.FilmShow;
import ru.job4j.cinema.persistence.FilmShowsDBStore;

import java.util.Collection;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 15/05/2022 - 12:48
 */
@Service
public class FilmShowsService {
    private final FilmShowsDBStore store;

    public FilmShowsService(FilmShowsDBStore store) {
        this.store = store;
    }

    public Collection<FilmShow> findAllShows() {
        return store.findAll();
    }
}
