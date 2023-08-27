package ru.kotikov.springbatch.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.kotikov.springbatch.models.AuthorMongo;
import ru.kotikov.springbatch.models.BookMongo;
import ru.kotikov.springbatch.models.CommentMongo;
import ru.kotikov.springbatch.models.GenreMongo;

import java.util.Map;

@Configuration
public class ItemReaderConfig {

    @Bean
    @StepScope
    public MongoItemReader<AuthorMongo> authorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("authorReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(Map.of())
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<GenreMongo> genreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("genreReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(Map.of())
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<BookMongo> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("bookItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(Map.of())
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<CommentMongo> commentReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<CommentMongo>()
                .name("commentReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(CommentMongo.class)
                .sorts(Map.of())
                .build();
    }

}
