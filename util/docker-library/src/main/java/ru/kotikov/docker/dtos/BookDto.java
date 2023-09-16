package ru.kotikov.docker.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kotikov.docker.models.Book;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String name;

    private Long authorId;

    private String authorName;

    private Long genreId;

    private String genreName;

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

