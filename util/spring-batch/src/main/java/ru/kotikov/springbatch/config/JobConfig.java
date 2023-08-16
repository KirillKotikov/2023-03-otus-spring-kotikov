package ru.kotikov.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.kotikov.springbatch.models.AuthorJpa;
import ru.kotikov.springbatch.models.AuthorMongo;
import ru.kotikov.springbatch.models.BookJpa;
import ru.kotikov.springbatch.models.BookMongo;
import ru.kotikov.springbatch.models.CommentJpa;
import ru.kotikov.springbatch.models.CommentMongo;
import ru.kotikov.springbatch.models.GenreJpa;
import ru.kotikov.springbatch.models.GenreMongo;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String TRANSFER_LIBRARY_JOB = "transferLibraryJob";

    private static final int CHUNK_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job transferLibraryJob(Step transferAuthorsStep, Step transferGenresStep,
                                  Step transferBooksStep, Step transferCommentsStep) {
        return new JobBuilder(TRANSFER_LIBRARY_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transferAuthorsStep)
                .next(transferGenresStep)
                .next(transferBooksStep)
                .next(transferCommentsStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step transferAuthorsStep(ItemReader<AuthorMongo> authorReader, ItemWriter<AuthorJpa> authorWriter,
                                    ItemProcessor<AuthorMongo, AuthorJpa> authorProcessor) {
        return new StepBuilder("transferAuthorsStep", jobRepository)
                .<AuthorMongo, AuthorJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения автора");
                    }

                    public void afterRead(@NonNull AuthorMongo author) {
                        logger.info("Конец чтения автора");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения автора");
                    }
                })
                .listener(new ItemWriteListener<AuthorJpa>() {
                    public void beforeWrite(@NonNull List<AuthorJpa> list) {
                        logger.info("Начало записи авторов");
                    }

                    public void afterWrite(@NonNull List<AuthorJpa> list) {
                        logger.info("Конец записи авторов");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List<AuthorJpa> list) {
                        logger.info("Ошибка записи авторов");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки авторов");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки авторов");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки авторов");
                    }
                })
                .build();
    }

    @Bean
    public Step transferGenresStep(ItemReader<GenreMongo> genreReader, ItemWriter<GenreJpa> genreWriter,
                                   ItemProcessor<GenreMongo, GenreJpa> genreItemProcessor) {
        return new StepBuilder("transferBooksStep", jobRepository)
                .<GenreMongo, GenreJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(genreReader)
                .processor(genreItemProcessor)
                .writer(genreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения жанров");
                    }

                    public void afterRead(@NonNull GenreMongo genre) {
                        logger.info("Конец чтения жанров");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения жанров");
                    }
                })
                .listener(new ItemWriteListener<GenreMongo>() {
                    public void beforeWrite(@NonNull List<GenreJpa> list) {
                        logger.info("Начало записи жанров");
                    }

                    public void afterWrite(@NonNull List<GenreJpa> list) {
                        logger.info("Конец записи жанров");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List<GenreMongo> list) {
                        logger.info("Ошибка записи жанров");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки жанров");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки жанров");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки жанров");
                    }
                })
                .build();
    }

    @Bean
    public Step transferBooksStep(ItemReader<BookMongo> bookItemReader, ItemWriter<BookJpa> writer,
                                  ItemProcessor<BookMongo, BookJpa> bookItemProcessor) {
        return new StepBuilder("transferBooksStep", jobRepository)
                .<BookMongo, BookJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookItemReader)
                .processor(bookItemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения книг");
                    }

                    public void afterRead(@NonNull BookMongo book) {
                        logger.info("Конец чтения книг");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения книг");
                    }
                })
                .listener(new ItemWriteListener<BookMongo>() {
                    public void beforeWrite(@NonNull List<BookJpa> list) {
                        logger.info("Начало записи книг");
                    }

                    public void afterWrite(@NonNull List<BookJpa> list) {
                        logger.info("Конец записи книг");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List<BookMongo> list) {
                        logger.info("Ошибка записи книг");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(@NonNull BookMongo book) {
                        logger.info("Начало обработки книг");
                    }

                    public void afterProcess(@NonNull BookMongo book1, BookJpa book2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull BookMongo book, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки книг");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки книг");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки книг");
                    }
                })
                .build();
    }


    @Bean
    public Step transferCommentsStep(ItemReader<CommentMongo> commentReader, ItemWriter<CommentJpa> commentWriter,
                                     ItemProcessor<CommentMongo, CommentJpa> commentItemProcessor) {
        return new StepBuilder("transferBooksStep", jobRepository)
                .<CommentMongo, CommentJpa>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(commentReader)
                .processor(commentItemProcessor)
                .writer(commentWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения комментариев");
                    }

                    public void afterRead(@NonNull CommentMongo comment) {
                        logger.info("Конец чтения комментариев");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения комментариев");
                    }
                })
                .listener(new ItemWriteListener<CommentMongo>() {
                    public void beforeWrite(@NonNull List<CommentJpa> list) {
                        logger.info("Начало записи комментариев");
                    }

                    public void afterWrite(@NonNull List<CommentJpa> list) {
                        logger.info("Конец записи комментариев");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List<CommentMongo> list) {
                        logger.info("Ошибка записи комментариев");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки комментариев");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки комментариев");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки комментариев");
                    }
                })
                .build();
    }

}
