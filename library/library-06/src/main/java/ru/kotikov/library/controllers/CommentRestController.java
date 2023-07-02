package ru.kotikov.library.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.services.CommentService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;


    @DeleteMapping("/api/book/{bookId}/comment/{commentId}")
    public void deleteComment(@PathVariable(value = "commentId") String commentId,
                              @PathVariable(value = "bookId") String bookId,
                              HttpServletResponse response) {
        commentService.deleteById(commentId);
        try {
            response.sendRedirect("/api/book");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/book/{bookId}/comment")
    public CommentDto saveComment(@Valid @RequestBody CommentDto comment) {
        return commentService.addComment(comment);
    }
}
