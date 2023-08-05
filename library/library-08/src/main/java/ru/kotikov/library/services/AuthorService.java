package ru.kotikov.library.services;

import ru.kotikov.library.dtos.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
}
