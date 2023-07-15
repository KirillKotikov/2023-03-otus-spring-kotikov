package ru.kotikov.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.kotikov.library.models.Author;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDto {
    private String id;

    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public Author toModel() {
        return new Author(getId(), getName());
    }
}
