package ru.job4j.cinema.controllers;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 12/05/2022 - 10:50
 */
@ThreadSafe
@Controller
public class UsersController {
    private final UserService userService;
    private static final String GUEST = "Guest";

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                              HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        return "user/addUser";
    }

    @PostMapping("/addUser")
    public String addUser(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.addUser(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Email or phone is invalid or already taken");
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/success";
    }

    @GetMapping("/formUpdateUser")
    public String formUpdateUser(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        return "user/updateUser";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        return "user/success";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        User user = getUser(session);
        model.addAttribute("user", user);
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername(GUEST);
        }
        return user;
    }
}
