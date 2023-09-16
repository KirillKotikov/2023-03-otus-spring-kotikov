package ru.kotikov.docker.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDto {
    private Long id;

    private String name;

}
