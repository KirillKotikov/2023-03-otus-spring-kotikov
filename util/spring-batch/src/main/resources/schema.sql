CREATE TABLE IF NOT EXISTS books
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255) UNIQUE,
    author_id BIGINT,
    genre_id  BIGINT
);

CREATE TABLE IF NOT EXISTS authors
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS genres
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS comments
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    text    text,
    book_id bigint references books (id) on delete cascade
);