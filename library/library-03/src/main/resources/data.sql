INSERT INTO authors (id, name) VALUES (1, 'Aladdin author');
INSERT INTO authors (id, name) VALUES (2, 'Alice author');
INSERT INTO authors (id, name) VALUES (3, 'Winnie-the-Pooh author');
INSERT INTO authors (id, name) VALUES (4, 'Snow White author');
INSERT INTO authors (id, name) VALUES (5, 'Cinderella author');

INSERT INTO genres (id, name) VALUES (1, 'Fairy tale');
INSERT INTO genres (id, name) VALUES (2, 'Fantasy');
INSERT INTO genres (id, name) VALUES (3, 'Classic');
INSERT INTO genres (id, name) VALUES (4, 'Love story');
INSERT INTO genres (id, name) VALUES (5, 'Adventure');

INSERT INTO books (id, name, author_id, genre_id) VALUES (1, 'Aladdin', 1, 1);
INSERT INTO books (id, name, author_id, genre_id) VALUES (2, 'Alice in wonderland', 2, 2);
INSERT INTO books (id, name, author_id, genre_id) VALUES (3, 'Winnie-the-Pooh', 3, 3);
INSERT INTO books (id, name, author_id, genre_id) VALUES (4, 'Snow White', 4, 4);
INSERT INTO books (id, name, author_id, genre_id) VALUES (5, 'Cinderella', 5, 5);

INSERT INTO comments (id, text, book_id) VALUES (1, 'Комментарий к Алладину', 1);
INSERT INTO comments (id, text, book_id) VALUES (2, 'Второй комментарий к Алладину', 1);
INSERT INTO comments (id, text, book_id) VALUES (3, 'Комментарий к Вини Пуху', 3);

