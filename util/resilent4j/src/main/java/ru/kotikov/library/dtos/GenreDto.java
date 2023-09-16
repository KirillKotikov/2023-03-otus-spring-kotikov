package ru.kotikov.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.kotikov.library.models.Genre;

@Getter
@Setter
@AllArgsConstructor
public class GenreDto {

    private String id;

    private String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre toModel() {
        return new Genre(getId(), getName());
    }
}
