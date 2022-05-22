CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS filmShows
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

INSERT INTO filmShows(name)
VALUES ('Avengers');
INSERT INTO filmShows(name)
VALUES ('Mission impossible');
INSERT INTO filmShows(name)
VALUES ('The Born Identity');

CREATE TABLE IF NOT EXISTS tickets
(
    id      SERIAL PRIMARY KEY,
    show_id INT NOT NULL REFERENCES filmShows (id),
    row     INT NOT NULL,
    seat    INT NOT NULL,
    user_id INT NOT NULL REFERENCES users (id),
    UNIQUE (show_id, row, seat)
);