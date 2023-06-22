package ru.kotikov.library.services;

import ru.kotikov.library.dtos.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAllGenres();
}
