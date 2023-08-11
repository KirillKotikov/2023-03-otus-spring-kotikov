package ru.kotikov.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private String authorId;

    private String authorName;

    private String genreId;

    private String genreName;

    public Book toModel() {
        return new Book(
                getId(),
                getName(),
                new Author(authorId, authorName),
                new Genre(genreId, genreName)
        );
    }

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getGenre().getId(),
                book.getGenre().getName()
        );
    }
}

