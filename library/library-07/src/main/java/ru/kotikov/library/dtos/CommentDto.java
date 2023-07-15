package ru.kotikov.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.kotikov.library.models.Comment;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private String id;

    private String text;

    private String bookId;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getBook().getId());
    }
}
