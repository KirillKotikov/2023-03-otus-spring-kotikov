package ru.kotikov.metric;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kotikov.metric.models.Author;
import ru.kotikov.metric.repositories.AuthorRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class Metric {

    private final AuthorRepository authorRepository;

    public static void main(String[] args) {
//        Console.main(args);
        SpringApplication.run(Metric.class);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 500; ++i) {
            authorRepository.save(new Author("Ivan"));
            authorRepository.save(new Author("Maria"));
            authorRepository.save(new Author("John"));
            authorRepository.save(new Author("Elena"));
            authorRepository.save(new Author("Anna"));
        }
    }

}
