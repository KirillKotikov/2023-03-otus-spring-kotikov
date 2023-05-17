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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void shouldHasSizeEqualTwo() {
        assertThat(jpaCommentRepository.findAll()).hasSize(2);
    }

    @DisplayName("искать комментарий по book_id")
    @Test
    public void shouldSearchByBookId() {
        List<Comment> byBook = jpaCommentRepository.findByBook(aladdin);
        assertThat(byBook).isNotNull();
        assertThat(byBook).hasSize(2);
        for (Comment comment : byBook) {
            assertEquals(comment.getBook().getId(), 1);
        }
    }

    @DisplayName("добавлять новый комментарий")
    @Test
    public void shouldAddComment() {
        assertThat(jpaCommentRepository.findAll()).hasSize(2);
        jpaCommentRepository.save(new Comment("Третий коммент Алладину", aladdin));
        assertThat(jpaCommentRepository.findAll()).hasSize(3);
    }

    @DisplayName("искать комментарий по id")
    @Test
    public void shouldSearchById() {
        Optional<Comment> optionalComment = jpaCommentRepository.findById(1);
        assertTrue(optionalComment.isPresent());
        assertEquals(optionalComment.get().getId(), 1);
        assertEquals(optionalComment.get().getText(), "Комментарий к Алладину");
        assertEquals(optionalComment.get().getBook().getId(), 1);
    }

    @DisplayName("обновлять комментарий")
    @Test
    public void shouldUpdateComment() {
        Comment comment = new Comment(1, "Изменили коммент", aladdin);
        Comment updated = jpaCommentRepository.save(comment);
        assertEquals(updated.getId(), 1);
        assertEquals(updated.getText(), "Изменили коммент");
        assertEquals(updated.getBook().getId(), 1);
    }

    @DisplayName("удалять комментарий")
    @Test
    public void shouldDeleteComment() {
        assertThat(jpaCommentRepository.findAll()).hasSize(2);
        jpaCommentRepository.deleteById(1);
        assertThat(jpaCommentRepository.findAll()).hasSize(1);
    }
}
