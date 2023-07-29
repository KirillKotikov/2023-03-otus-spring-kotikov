package ru.kotikov.library.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kotikov.library.dtos.AuthorDto;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;

    @GetMapping("/api/author/")
    public Flux<AuthorDto> getAll() {
        return repository.findAll()
                .map(AuthorDto::toDto);
    }

    @GetMapping("/api/author/{id}")
    public Mono<ResponseEntity<AuthorDto>> getById(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(AuthorDto::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));

    }

    @PostMapping("/api/author")
    public Mono<Author> saveAuthor(@Valid @RequestBody Author author) {
        return repository.save(author);
    }

    @PutMapping("/api/author")
    public Mono<Author> updateAuthor(@Valid @RequestBody Author author) {
        return repository.save(author);
    }

    @DeleteMapping("/api/author/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable("id") String id) {
        return repository.deleteById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));
    }
    
}
