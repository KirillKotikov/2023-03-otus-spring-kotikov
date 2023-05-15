package ru.kotikov.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.repositories.AuthorRepository;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public long countAllBooks() {
        return bookRepository.count();

    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book addBook(String name, long authorId, long genreId) {
        Author author = authorRepository.findById(authorId).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.AUTHOR_NOT_FOUND, authorId)));
        Genre genre = genreRepository.findById(genreId).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.GENRE_NOT_FOUND, genreId)));
        return bookRepository.save(new Book(name, author, genre));
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        Optional<Book> resultBook = bookRepository.findById(id);
        if (resultBook.isEmpty()) {
            throw new DataNotFoundException(String.format(ExceptionMessages.BOOK_NOT_FOUND, id));
        }
        return resultBook.get();
    }

    @Override
    @Transactional
    public Book updateBook(long id, String name, Long authorId, Long genreId) {
        Book bookForUpdate = bookRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.BOOK_NOT_FOUND, id)));
        Author author = authorRepository.findById(authorId).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.AUTHOR_NOT_FOUND, authorId)));
        bookForUpdate.setAuthor(author);
        Genre genre = genreRepository.findById(genreId).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.GENRE_NOT_FOUND, genreId)));
        bookForUpdate.setGenre(genre);
        bookForUpdate.setName(name);
        return bookRepository.save(bookForUpdate);
}

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }
}