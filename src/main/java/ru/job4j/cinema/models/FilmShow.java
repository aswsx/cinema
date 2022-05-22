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
public class FilmShow implements Serializable {
    private int id;
    private String name;

    public FilmShow(String name) {
        this.name = name;
    }
}
