package ru.kotikov.springbatch.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.kotikov.springbatch.models.AuthorJpa;
import ru.kotikov.springbatch.models.BookJpa;
import ru.kotikov.springbatch.models.BookMongo;
import ru.kotikov.springbatch.models.GenreJpa;
import ru.kotikov.springbatch.repositories.AuthorJpaRepository;
import ru.kotikov.springbatch.repositories.GenreJpaRepository;

@Component
@RequiredArgsConstructor
public class BookItemProcessor implements ItemProcessor<BookMongo, BookJpa> {

    private final AuthorJpaRepository authorJpaRepository;

    private final GenreJpaRepository genreJpaRepository;

    @Override
    public BookJpa process(BookMongo bookMongo) {
        AuthorJpa authorJpa = authorJpaRepository.findByName(bookMongo.getAuthor().getName());
        GenreJpa genreJpa = genreJpaRepository.findByName(bookMongo.getGenre().getName());
        return new BookJpa(bookMongo.getName(), authorJpa, genreJpa);
    }

}
