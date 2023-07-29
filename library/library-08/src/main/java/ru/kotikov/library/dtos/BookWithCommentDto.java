package ru.kotikov.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookWithCommentDto {

    private BookDto bookDto;

    private List<CommentDto> commentDtoList;
}
