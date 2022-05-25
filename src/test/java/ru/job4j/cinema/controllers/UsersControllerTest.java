package ru.job4j.cinema.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.services.UserService;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 24/05/2022 - 21:49
 */
class UsersControllerTest {

    @Test
    void addUserSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        User user = new User(1, "Alex", "123@gmail.com", "1234567", "12345");
        UsersController controller = new UsersController(userService);
        controller.addUser(model, user);
        verify(userService).addUser(user);
    }

    @Test
    void addUserFail() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        User user = new User(1, "Alex", "123@gmail.com", "1234567", "12345");
        UsersController controller = new UsersController(userService);
        String page = controller.addUser(model, user);
        verify(userService).addUser(user);
        assertThat(page, is("redirect:/formAddUser?fail=true"));
    }

    @Test
    void updateUser() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        User user = new User(1, "Alex", "123@gmail.com", "1234567", "12345");
        User updUser = new User(1, "Tom", "123@gmail.com", "1234567", "12345");
        UsersController controller = new UsersController(userService);
        controller.addUser(model, user);
        controller.updateUser(updUser);
        verify(userService).updateUser(updUser);
    }
}