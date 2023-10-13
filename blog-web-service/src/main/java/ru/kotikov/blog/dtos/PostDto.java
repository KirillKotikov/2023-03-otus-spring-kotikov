package ru.kotikov.blog.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private String id;

    @JsonProperty(required = true)
    @NotBlank(message = "text is mandatory!")
    private String text;

    @JsonProperty(required = true)
    @NotBlank(message = "header is mandatory")
    private String header;

    private String userNickName;

    private String userLogin;

}
