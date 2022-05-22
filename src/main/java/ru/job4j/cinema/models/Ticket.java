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
public class Ticket implements Serializable {
    private int id;
    private int row;
    private int seat;
    private int showId;
    private int userId;
    private FilmShow show;

    public Ticket(int row, int seat, FilmShow show, int userId) {
        this.row = row;
        this.seat = seat;
        this.show = show;
        this.userId = userId;
    }
}
