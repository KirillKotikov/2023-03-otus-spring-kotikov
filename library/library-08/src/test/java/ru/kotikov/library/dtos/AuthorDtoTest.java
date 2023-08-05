package ru.kotikov.library.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kotikov.library.models.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("dto автора должно ")
public class AuthorDtoTest {

    @Test
    @DisplayName("конвертировать dto в model")
    public void shouldConvertDtoToModel() {
        AuthorDto authorDto = new AuthorDto("1", "author");
        Author author = authorDto.toModel();
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
    }

    @Test
    @DisplayName("конвертировать model в dto")
    public void shouldConvertModelToDto() {
        Author author = new Author("1", "author");
        AuthorDto authorDto = AuthorDto.toDto(author);
        assertEquals(author.getId(), authorDto.getId());
        assertEquals(author.getName(), authorDto.getName());
    }
}
