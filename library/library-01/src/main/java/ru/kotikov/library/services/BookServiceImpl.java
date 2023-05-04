package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.dao.AuthorDao;
import ru.kotikov.library.dao.BookDao;
import ru.kotikov.library.dao.GenreDao;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public int countAllBooks() {
        return bookDao.count();

    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book addBook(String name, long authorId, long genreId)
            throws DataNotFoundException {
        Author author = authorDao.getById(authorId);
        if (author == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.AUTHOR_NOT_FOUND, authorId));
        }
        Genre genre = genreDao.getById(genreId);
        if (genre == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.GENRE_NOT_FOUND, genreId));
        }
        return bookDao.insert(new Book(name, author, genre));
    }

    @Override
    public Book getBookById(long id) throws DataNotFoundException {
        Book resultBook = bookDao.getById(id);
        if (resultBook == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.BOOK_NOT_FOUND, id));
        }
        return resultBook;
    }

    @Override
    public Book updateBook(long id, String name, Long authorId, Long genreId)
            throws DataNotFoundException {
        Book bookForUpdate = bookDao.getById(id);
        if (bookForUpdate == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.BOOK_NOT_FOUND, id));
        }
        Author author = authorDao.getById(authorId);
        if (author == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.AUTHOR_NOT_FOUND, authorId));
        }
        bookForUpdate.setAuthor(author);
        Genre genre = genreDao.getById(genreId);
        if (genre == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.GENRE_NOT_FOUND, genreId));
        }
        bookForUpdate.setGenre(genre);
        bookForUpdate.setName(name);
        return bookDao.update(bookForUpdate);
    }

    @Override
    public int deleteBookById(long id) {
        return bookDao.deleteById(id);
    }
}
