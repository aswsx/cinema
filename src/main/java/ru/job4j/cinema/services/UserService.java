package ru.job4j.cinema.services;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.models.User;
import ru.job4j.cinema.persistence.UsersDBStore;

import java.util.Optional;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 12/05/2022 - 10:51
 */
@Service
public class UserService {
    private final UsersDBStore store;

    public UserService(UsersDBStore store) {
        this.store = store;
    }

    public Optional<User> addUser(User user) {
        return store.addUser(user);
    }

    public void updateUser(User user) {
        store.updateUser(user);
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}