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
import ru.kotikov.library.dtos.GenreDto;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository repository;

    @GetMapping("/api/genre/")
    public Flux<GenreDto> getAll() {
        return repository.findAll()
                .map(GenreDto::toDto);
    }

    @GetMapping("/api/genre/{id}")
    public Mono<ResponseEntity<GenreDto>> getById(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(GenreDto::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));

    }

    @PostMapping("/api/genre")
    public Mono<Genre> saveGenre(@Valid @RequestBody Genre genre) {
        return repository.save(genre);
    }

    @PutMapping("/api/genre")
    public Mono<Genre> updateGenre(@Valid @RequestBody Genre genre) {
        return repository.save(genre);
    }

    @DeleteMapping("/api/genre/{id}")
    public Mono<ResponseEntity<Void>> deleteGenre(@PathVariable("id") String id) {
        return repository.deleteById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));
    }

}
