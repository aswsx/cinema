package ru.job4j.cinema.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.models.FilmShow;
import ru.job4j.cinema.services.FilmShowsService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 24/05/2022 - 21:49
 */
class FilmShowsControllerTest {

    @Test
    void showSelectTest() {
        List<FilmShow> shows = Arrays.asList(
                new FilmShow(1, "Avengers"),
                new FilmShow(2, "Uncharted"));
        Model model = mock(Model.class);
        FilmShowsService service = mock(FilmShowsService.class);
        when(service.findAllShows()).thenReturn(shows);
        FilmShowsController controller = new FilmShowsController(service);
        String page = controller.showSelect(model);
        verify(model).addAttribute("shows", shows);
        assertThat(page, is("cinema/shows"));
    }
}