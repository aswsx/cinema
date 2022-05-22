package ru.job4j.cinema.models;

import lombok.Getter;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 17/05/2022 - 16:16
 */
@Getter
public class Hall {
    private final int rows;
    private final int seats;

    public Hall() {
        this.rows = 6;
        this.seats = 8;
    }
}
