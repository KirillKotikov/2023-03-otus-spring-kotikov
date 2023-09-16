package ru.kotikov.library.services;

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

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

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
