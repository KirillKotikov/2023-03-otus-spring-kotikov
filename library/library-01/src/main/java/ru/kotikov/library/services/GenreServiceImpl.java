package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.repositories.GenreDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }
}
