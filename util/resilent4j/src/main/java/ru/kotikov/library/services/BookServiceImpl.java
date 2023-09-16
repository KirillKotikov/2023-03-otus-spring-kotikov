package ru.kotikov.library.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private @Getter
    List<BookDto> allCache = new ArrayList<>();

    private @Getter
    final Map<String, BookWithCommentDto> byIdCache = new HashMap<>();

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        sleepRandom();
        List<BookDto> allBooks = bookRepository.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        allCache = allBooks;
        return allBooks;
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithCommentDto getBookWithCommentsByBookId(String id) {
        sleepRandom();
        BookDto bookDto = BookDto.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format(ExceptionMessages.BOOK_NOT_FOUND, id)
                )));
        List<CommentDto> commentDtoList =
                commentRepository.findByBookId(id).stream()
                        .map(CommentDto::toDto)
                        .toList();
        BookWithCommentDto bookWithCommentDto =
                new BookWithCommentDto(bookDto, commentDtoList);
        byIdCache.put(id, bookWithCommentDto);
        return bookWithCommentDto;

    }

    private void sleepRandom() {
        Random random = new Random();
        if (random.nextInt(3) == 2) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
