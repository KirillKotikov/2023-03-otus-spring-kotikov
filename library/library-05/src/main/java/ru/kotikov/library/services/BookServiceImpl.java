package ru.kotikov.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.repositories.AuthorRepository;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.CommentRepository;
import ru.kotikov.library.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(String id) {
        return BookDto.toDto(bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format(ExceptionMessages.BOOK_NOT_FOUND, id))));
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithCommentDto getBookWithCommentsByBookId(String id) {
        BookDto bookDto = BookDto.toDto(bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format(ExceptionMessages.BOOK_NOT_FOUND, id))));
        List<CommentDto> commentDtoList = commentRepository.findByBookId(id).stream().map(CommentDto::toDto).toList();
        return new BookWithCommentDto(bookDto, commentDtoList);
    }

    @Override
    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        if (bookDto.getId() != null) {
            Book bookForUpdate = bookRepository.findById(bookDto.getId())
                    .orElseThrow(() -> new DataNotFoundException(
                            String.format(ExceptionMessages.BOOK_NOT_FOUND, bookDto.getId())));
            Author author = authorRepository.findById(bookDto.getAuthorId())
                    .orElseThrow(() -> new DataNotFoundException(
                            String.format(ExceptionMessages.AUTHOR_NOT_FOUND, bookDto.getAuthorId())));
            bookForUpdate.setAuthor(author);
            Genre genre = genreRepository.findById(bookDto.getGenreId())
                    .orElseThrow(() -> new DataNotFoundException(
                            String.format(ExceptionMessages.GENRE_NOT_FOUND, bookDto.getGenreId())));
            bookForUpdate.setGenre(genre);
            bookForUpdate.setName(bookDto.getName());
            return BookDto.toDto(bookRepository.save(bookForUpdate));
        } else {
            Author author = authorRepository.findById(bookDto.getAuthorId())
                    .orElseThrow(() -> new DataNotFoundException(
                            String.format(ExceptionMessages.AUTHOR_NOT_FOUND, bookDto.getAuthorId())));
            Genre genre = genreRepository.findById(bookDto.getGenreId())
                    .orElseThrow(() -> new DataNotFoundException(
                            String.format(ExceptionMessages.GENRE_NOT_FOUND, bookDto.getGenreId())));
            return BookDto.toDto(bookRepository.save(new Book(bookDto.getName(), author, genre)));
        }
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }
}