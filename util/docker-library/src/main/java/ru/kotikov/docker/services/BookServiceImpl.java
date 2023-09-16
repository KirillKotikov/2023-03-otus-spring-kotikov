package ru.kotikov.docker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.docker.Exceptions.DataNotFoundException;
import ru.kotikov.docker.constants.ExceptionMessages;
import ru.kotikov.docker.dtos.BookDto;
import ru.kotikov.docker.dtos.BookWithCommentDto;
import ru.kotikov.docker.dtos.CommentDto;
import ru.kotikov.docker.models.Book;
import ru.kotikov.docker.repositories.BookRepository;
import ru.kotikov.docker.repositories.CommentRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements ru.kotikov.docker.services.BookService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        sleepRandom();
        return bookRepository.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithCommentDto getBookWithCommentsByBookId(Long id) {
        sleepRandom();
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format(ExceptionMessages.BOOK_NOT_FOUND, id)
                ));
        BookDto bookDto = BookDto.toDto(book);
        List<CommentDto> commentDtoList =
                commentRepository.findByBook(book).stream()
                        .map(CommentDto::toDto)
                        .toList();
        return new BookWithCommentDto(bookDto, commentDtoList);

    }

    private void sleepRandom() {
        Random random = new Random();
        if (random.nextInt(3) == 2) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
