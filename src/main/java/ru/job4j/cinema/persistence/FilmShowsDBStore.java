package ru.job4j.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.models.FilmShow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 15/05/2022 - 13:06
 */
@Repository
public class FilmShowsDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(FilmShowsDBStore.class);
    private final BasicDataSource pool;

    public FilmShowsDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<FilmShow> findAll() {
        List<FilmShow> filmShows = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * from filmshows")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    filmShows.add(new FilmShow(
                            it.getInt("id"),
                            it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Find All Shows Error", e);
        }
        return filmShows;
    }
}

