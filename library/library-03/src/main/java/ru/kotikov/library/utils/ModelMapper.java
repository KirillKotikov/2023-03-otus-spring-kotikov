package ru.kotikov.library.utils;

import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.models.Genre;

import java.util.List;

public class ModelMapper {
    public static String mapAuthorToString(Author author) {
        return "- id = " + author.getId() + ", имя автора = " + author.getName();
    }

    public static String mapBookToString(Book book) {
        return "- id = " + book.getId() + ", название книги = " + book.getName() +
                ", автор = " + book.getAuthor().getName() + ", жанр = " + book.getGenre().getName();
    }

    public static String mapGenreToString(Genre genre) {
        return "- id = " + genre.getId() + ", название жанра = " + genre.getName();
    }

    public static String mapCommentToString(Comment comment) {
        return "- id = " + comment.getId() + ", текст комментария = " + comment.getText();
    }

    public static String mapCommentToString(Comment comment, String bookName) {
        return "- id = " + comment.getId() + ", текст комментария = " + comment.getText()
                + ", оставлен для книги = " + bookName;
    }

    public static String formatBooks(List<Book> allBooks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : allBooks) {
            stringBuilder.append(ModelMapper.mapBookToString(book)).append("\n");
        }
        return stringBuilder.toString();
    }
}
