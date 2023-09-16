package ru.kotikov.docker.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookWithCommentDto {

    private ru.kotikov.docker.dtos.BookDto bookDto;

    private List<ru.kotikov.docker.dtos.CommentDto> commentDtoList;
}
