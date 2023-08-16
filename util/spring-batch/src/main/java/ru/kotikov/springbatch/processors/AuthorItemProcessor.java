package ru.kotikov.springbatch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.kotikov.springbatch.models.AuthorJpa;
import ru.kotikov.springbatch.models.AuthorMongo;

@Component
public class AuthorItemProcessor implements ItemProcessor<AuthorMongo, AuthorJpa> {

    @Override
    public AuthorJpa process(AuthorMongo authorMongo) {
        return new AuthorJpa(authorMongo.getName());
    }

}

