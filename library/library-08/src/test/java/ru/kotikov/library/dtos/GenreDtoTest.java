package ru.kotikov.library.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kotikov.library.models.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("dto жанра должно ")
public class GenreDtoTest {

    @Test
    @DisplayName("конвертировать dto в model")
    public void shouldConvertDtoToModel() {
        GenreDto genreDto = new GenreDto("1", "genre");
        Genre genre = genreDto.toModel();
        assertEquals(genre.getId(), genreDto.getId());
        assertEquals(genre.getName(), genreDto.getName());
    }

    @Test
    @DisplayName("конвертировать model в dto")
    public void shouldConvertModelToDto() {
        Genre genre = new Genre("1", "genre");
        GenreDto genreDto = GenreDto.toDto(genre);
        assertEquals(genre.getId(), genreDto.getId());
        assertEquals(genre.getName(), genreDto.getName());
    }
}
