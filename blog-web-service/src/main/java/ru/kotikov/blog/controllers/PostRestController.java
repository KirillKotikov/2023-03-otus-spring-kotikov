package ru.kotikov.blog.controllers;

import jakarta.validation.Valid;
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
import ru.kotikov.blog.dtos.PostDto;
import ru.kotikov.blog.services.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/api/v1/post")
    public ResponseEntity<Object> getAllPostList() {
        List<PostDto> allPostList = postService.getAllPostList();
        return ResponseEntity.ok(allPostList);
    }

    @GetMapping("/api/v1/post/user/{login}")
    public ResponseEntity<Object> getPostListByUserLogin(@PathVariable @NotNull String login) {
        try {
            return ResponseEntity.ok(postService.getPostListByUserLogin(login));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/api/v1/post")
    public ResponseEntity<Object> addPost(
            @RequestBody @Valid @NotNull PostDto postDto) {
        try {
            return ResponseEntity.ok(postService.addPost(postDto));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/api/v1/post")
    public ResponseEntity<Object> updatePost(
            @RequestBody @Valid @NotNull PostDto postDto) {
        try {
            return ResponseEntity.ok(postService.updatePost(postDto));
        } catch (DataNotFoundException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable @NotNull String id) {
        try {
            postService.deletePostById(id);
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
