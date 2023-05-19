INSERT INTO authors (name) VALUES ('Aladdin author');
INSERT INTO authors (name) VALUES ('Alice author');
INSERT INTO authors (name) VALUES ('Winnie-the-Pooh author');
INSERT INTO authors (name) VALUES ('Snow White author');
INSERT INTO authors (name) VALUES ('Cinderella author');

INSERT INTO genres (name) VALUES ('Fairy tale');
INSERT INTO genres (name) VALUES ('Fantasy');
INSERT INTO genres (name) VALUES ('Classic');
INSERT INTO genres (name) VALUES ('Love story');
INSERT INTO genres (name) VALUES ('Adventure');

INSERT INTO books (name, author_id, genre_id) VALUES ('Aladdin', 1, 1);
INSERT INTO books (name, author_id, genre_id) VALUES ('Alice in wonderland', 2, 2);
INSERT INTO books (name, author_id, genre_id) VALUES ('Winnie-the-Pooh', 3, 3);
INSERT INTO books (name, author_id, genre_id) VALUES ('Snow White', 4, 4);
INSERT INTO books (name, author_id, genre_id) VALUES ('Cinderella', 5, 5);

INSERT INTO comments (text, book_id) VALUES ('Комментарий к Алладину', 1);
INSERT INTO comments (text, book_id) VALUES ('Второй комментарий к Алладину', 1);
INSERT INTO comments (text, book_id) VALUES ('Комментарий к Вини Пуху', 3);

