package ru.job4j.cinema.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.models.FilmShow;
import ru.job4j.cinema.services.FilmShowsService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 24/05/2022 - 21:49
 */
class IndexControllerTest {

    @Test
    void indexTest() {
        List<FilmShow> shows = Arrays.asList(
                new FilmShow(1, "Avengers"),
                new FilmShow(2, "Uncharted"));
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        FilmShowsService service = mock(FilmShowsService.class);
        when(service.findAllShows()).thenReturn(shows);
        IndexController controller = new IndexController(service);
        String page = controller.index(model, session, false);
        verify(model).addAttribute("filmShows", shows);
        assertThat(page, is("index"));
    }
}