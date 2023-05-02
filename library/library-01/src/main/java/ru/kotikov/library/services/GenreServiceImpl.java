package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.dao.GenreDao;
import ru.kotikov.library.models.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final GenreDao genreDao;

    @Override
    public String getAllGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : genreDao.getAll()) {
            stringBuilder.append(genre).append("\n");
        }
        return stringBuilder.toString();
    }
}
