package ru.kotikov.library.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("dto комментария должно ")
public class CommentDtoTest {

    @Test
    @DisplayName("конвертировать model в dto")
    public void shouldConvertModelToDto() {
        Comment comment = new Comment("1", "text",
                new Book("1", "name", null, null));
        CommentDto commentDto = CommentDto.toDto(comment);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getText(), commentDto.getText());
        assertEquals(comment.getBook().getId(), commentDto.getBookId());
    }
}
