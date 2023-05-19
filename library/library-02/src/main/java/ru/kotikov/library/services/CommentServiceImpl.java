package ru.kotikov.library.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBook(Book book) {
        return commentRepository.findByBook(book);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateCommentText(long commentId, String text) {
        Comment commentForUpdate = getById(commentId);
        commentForUpdate.setText(text);
        return commentRepository.save(commentForUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(()
                -> new DataNotFoundException(String.format(ExceptionMessages.COMMENT_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public int deleteById(long id) {
        return commentRepository.deleteById(id);
    }
}
