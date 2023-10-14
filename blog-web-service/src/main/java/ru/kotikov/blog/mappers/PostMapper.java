package ru.kotikov.blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kotikov.blog.dtos.PostDto;
import ru.kotikov.blog.models.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.nickName", target = "userNickName")
    @Mapping(source = "user.login", target = "userLogin")
    PostDto postToPostDto(Post post);

    @Mapping(target = "user", ignore = true)
    Post postDtoToPost(PostDto postDto);

    @Mapping(source = "user.nickName", target = "userNickName")
    @Mapping(source = "user.login", target = "userLogin")
    List<PostDto> postListToPostDtoList(List<Post> post);

}
