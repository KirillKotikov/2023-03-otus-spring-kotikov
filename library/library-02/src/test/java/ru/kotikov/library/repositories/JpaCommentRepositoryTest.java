package ru.kotikov.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями должен ")
@DataJpaTest
@Import(JpaCommentRepository.class)
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository jpaCommentRepository;

    private Book aladdin;

    @BeforeEach
    public void initialize() {
        aladdin = new Book(1, "Aladdin",
                new Author(1, "Aladdin author"),
                new Genre(1, "Fairy tale"));
    }

    @DisplayName("искать все комментарии")
    @Test
    public void shouldShowAllComments() {
        assertThat(jpaCommentRepository.findAll()).hasSize(2);
        assertThat(jpaCommentRepository.findAll()).containsAll(List.of(
                new Comment(1, "Комментарий к Алладину", aladdin),
                new Comment(2, "Второй комментарий к Алладину", aladdin)
        ));
    }

    @DisplayName("искать комментарий по book_id")
    @Test
    public void shouldSearchByBookId() {
        assertThat(jpaCommentRepository.findByBook(aladdin)).isNotNull();
        assertThat(jpaCommentRepository.findByBook(aladdin)).containsAll(List.of(
                new Comment(1, "Комментарий к Алладину", aladdin),
                new Comment(2, "Второй комментарий к Алладину", aladdin)));
    }

    @DisplayName("добавлять новый комментарий")
    @Test
    public void shouldAddComment() {
        jpaCommentRepository.save(new Comment("Третий коммент Алладину", aladdin));
        assertThat(jpaCommentRepository.findAll()).hasSize(3);
        assertThat(jpaCommentRepository.findAll())
                .contains(new Comment(3, "Третий коммент Алладину", aladdin));
    }

    @DisplayName("искать комментарий по id")
    @Test
    public void shouldSearchById() {
        assertThat(jpaCommentRepository.findById(1)).isNotNull();
        assertThat(jpaCommentRepository.findById(1)).isPresent().get()
                .isEqualTo(new Comment(1, "Комментарий к Алладину", aladdin));
    }

    @DisplayName("обновлять комментарий")
    @Test
    public void shouldUpdateComment() {
        Comment comment = new Comment(1, "Изменили коммент", aladdin);
        assertThat(jpaCommentRepository.save(comment)).isEqualTo(comment);
    }

    @DisplayName("удалять комментарий")
    @Test
    public void shouldDeleteComment() {
        assertThat(jpaCommentRepository.findAll()).hasSize(2);
        jpaCommentRepository.deleteById(1);
        assertThat(jpaCommentRepository.findAll()).hasSize(1);
        assertThat(jpaCommentRepository.findAll())
                .contains(new Comment(2, "Второй комментарий к Алладину", aladdin));
    }
}
