package ru.kotikov.springbatch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.kotikov.springbatch.models.GenreJpa;
import ru.kotikov.springbatch.models.GenreMongo;

@Component
public class GenreItemProcessor implements ItemProcessor<GenreMongo, GenreJpa> {

    @Override
    public GenreJpa process(GenreMongo genreMongo) {
        return new GenreJpa(genreMongo.getName());
    }

}
