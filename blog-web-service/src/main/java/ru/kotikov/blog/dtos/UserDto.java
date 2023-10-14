package ru.kotikov.blog.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String nickName;

    @JsonProperty(required = true)
    @NotEmpty(message = "login is mandatory!")
    @NotNull(message = "login is mandatory!")
    private String login;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty(required = true)
    @NotEmpty(message = "password is mandatory!")
    @NotNull(message = "password is mandatory!")
    private char[] password;

    public String getPassword() {
        if (password != null && password.length > 0) {
            return new String(password);
        } else {
            return "******";
        }
    }

    public void setPassword(String password) {
        this.password = password.toCharArray();
    }
}
