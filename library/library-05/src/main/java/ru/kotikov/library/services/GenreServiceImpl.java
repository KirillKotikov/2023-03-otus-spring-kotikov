package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.dtos.GenreDto;
import ru.kotikov.library.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }
}
