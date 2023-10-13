package ru.kotikov.blog.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String id;

    @JsonProperty(required = true)
    @NotNull(message = "text is mandatory!")
    private String text;

    @JsonProperty(required = true)
    @NotNull(message = "postId is mandatory!")
    private String postId;

    private String userNickName;

    @JsonProperty(required = true)
    @NotNull(message = "userLogin is mandatory!!")
    private String userLogin;

}
