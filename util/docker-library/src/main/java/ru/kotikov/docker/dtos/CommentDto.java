package ru.kotikov.docker.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.kotikov.docker.models.Comment;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String text;

    private Long bookId;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getBook().getId());
    }
}
