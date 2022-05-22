package ru.job4j.cinema.models;

import lombok.*;

import java.io.Serializable;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 09/05/2022 - 21:01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private int userId;
    private String username;
    private String email;
    private String phone;
    private String password;

    @Override
    public String toString() {
        return String.format("%s", this.username);
    }
}
