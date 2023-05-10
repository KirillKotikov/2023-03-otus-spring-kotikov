package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.dao.AuthorDao;
import ru.kotikov.library.models.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
       return authorDao.getAll();
    }
}
