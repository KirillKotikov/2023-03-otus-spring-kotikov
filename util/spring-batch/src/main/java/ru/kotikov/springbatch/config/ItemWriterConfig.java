package ru.kotikov.springbatch.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kotikov.springbatch.models.AuthorJpa;
import ru.kotikov.springbatch.models.BookJpa;
import ru.kotikov.springbatch.models.CommentJpa;
import ru.kotikov.springbatch.models.GenreJpa;

@Configuration
public class ItemWriterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @StepScope
    public JpaItemWriter<AuthorJpa> authorWriter() {
        JpaItemWriter<AuthorJpa> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return jpaItemWriter;
    }

    @Bean
    @StepScope
    public JpaItemWriter<GenreJpa> genreWriter() {
        JpaItemWriter<GenreJpa> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return jpaItemWriter;
    }

    @Bean
    @StepScope
    public JpaItemWriter<BookJpa> bookWriter() {
        JpaItemWriter<BookJpa> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return jpaItemWriter;
    }

    @Bean
    @StepScope
    public JpaItemWriter<CommentJpa> commentWriter() {
        JpaItemWriter<CommentJpa> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return jpaItemWriter;
    }

}
