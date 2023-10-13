package ru.kotikov.blog.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.blog.Exceptions.DataNotFoundException;
import ru.kotikov.blog.Utils.HandlerUtil;
import ru.kotikov.blog.dtos.CommentDto;
import ru.kotikov.blog.services.CommentService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/api/v1/post/{postId}/comment")
    public ResponseEntity<Object> getCommentListByPostId(@PathVariable @NotNull String postId) {
        try {
            return ResponseEntity.ok(commentService.getCommentListByPostId(postId));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/api/v1/post/{postId}/comment/user/{login}")
    public ResponseEntity<Object> getCommentListByPostIdAndLogin(
            @PathVariable @NotNull String postId,
            @PathVariable @NotNull String login) {
        try {
            return ResponseEntity.ok(
                    commentService.getCommentListByPostIdAndUserLogin(postId, login)
            );
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/api/v1/comment")
    public ResponseEntity<Object> addComment(
            @RequestBody @NotNull CommentDto commentDto) {
        try {
            return ResponseEntity.ok(commentService.addComment(commentDto));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/api/v1/comment")
    public ResponseEntity<Object> updateComment(
            @RequestBody @NotNull CommentDto commentDto) {
        try {
            return ResponseEntity.ok(commentService.updateComment(commentDto));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/api/v1/comment/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable @NotNull String id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return HandlerUtil.getErrors(ex);
    }

}
