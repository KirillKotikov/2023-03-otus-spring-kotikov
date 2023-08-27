package ru.kotikov.springbatch.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.kotikov.springbatch.models.BookJpa;
import ru.kotikov.springbatch.models.CommentJpa;
import ru.kotikov.springbatch.models.CommentMongo;
import ru.kotikov.springbatch.repositories.BookJpaRepository;

@Component
@RequiredArgsConstructor
public class CommentItemProcessor implements ItemProcessor<CommentMongo, CommentJpa> {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public CommentJpa process(CommentMongo commentMongo) {
        BookJpa bookJpa = bookJpaRepository.findByName(commentMongo.getBook().getName());
        return new CommentJpa(commentMongo.getText(), bookJpa);
    }

}
