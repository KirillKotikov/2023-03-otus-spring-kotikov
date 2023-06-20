package ru.kotikov.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.CommentRepository;

import java.util.List;

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
    public List<Comment> getByBook(Book book) {
        return commentRepository.findByBook(book);
    }

    @Override
    @Transactional
    public Comment addComment(String bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new DataNotFoundException(
                String.format(ExceptionMessages.BOOK_NOT_FOUND, bookId)));
        return commentRepository.save(new Comment(text, book));
    }

    @Override
    @Transactional
    public Comment updateCommentText(String commentId, String text) {
        Comment commentForUpdate = getById(commentId);
        commentForUpdate.setText(text);
        return commentRepository.save(commentForUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format(ExceptionMessages.COMMENT_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
