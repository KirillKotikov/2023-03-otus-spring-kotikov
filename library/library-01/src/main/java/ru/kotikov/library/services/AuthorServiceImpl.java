package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.dao.AuthorDao;
import ru.kotikov.library.models.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public String getAllAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author : authorDao.getAll()) {
            stringBuilder.append(author).append("\n");
        }
        return stringBuilder.toString();
    }
}
