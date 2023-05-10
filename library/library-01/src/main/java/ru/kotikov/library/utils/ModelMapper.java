package ru.kotikov.library.utils;

import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

public class ModelMapper {

    public static String mapModelToString(Object model) {
        if (model instanceof Author author) {
            return "- id = " + author.getId() + ", имя автора = " + author.getName();
        } else if (model instanceof Book book) {
            return "- id = " + book.getId() + ", название книги = " + book.getName() +
                    ", автор = " + book.getAuthor().getName() + ", жанр = " + book.getGenre().getName();
        } else if (model instanceof Genre genre) {
            return "- id = " + genre.getId() + ", название жанра = " + genre.getName();
        } else {
            return null;
        }
    }
}
