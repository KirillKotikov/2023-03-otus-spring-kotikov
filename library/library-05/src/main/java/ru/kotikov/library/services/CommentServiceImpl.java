package ru.kotikov.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getByBook(Book book) {
        return commentRepository.findByBook(book).stream()
                .map(CommentDto::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByBookId(String bookId) {
        return commentRepository.findByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto addComment(String bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new DataNotFoundException(
                String.format(ExceptionMessages.BOOK_NOT_FOUND, bookId)));
        return CommentDto.toDto(commentRepository.save(new Comment(text, book)));
    }

    @Override
    @Transactional
    public CommentDto updateCommentText(String commentId, String text) {
        Comment commentForUpdate = commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException(String.format(
                        ExceptionMessages.COMMENT_NOT_FOUND, commentId)));
        commentForUpdate.setText(text);
        return CommentDto.toDto(commentRepository.save(commentForUpdate));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getById(String id) {
        return CommentDto.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(ExceptionMessages.COMMENT_NOT_FOUND, id))));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
