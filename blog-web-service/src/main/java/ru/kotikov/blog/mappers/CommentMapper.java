package ru.kotikov.blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kotikov.blog.dtos.CommentDto;
import ru.kotikov.blog.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentDtoToComment(CommentDto commentDto);

    @Mapping(source = "user.nickName", target = "userNickName")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    CommentDto commentToCommentDto(Comment comment);

    List<Comment> commentDtoListToCommentList(List<CommentDto> commentDtoList);

    @Mapping(source = "user.nickName", target = "userNickName")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    List<CommentDto> commentListToCommentDtoList(List<Comment> commentList);

}
