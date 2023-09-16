package ru.kotikov.library.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
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
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Book aladdin;

    @BeforeEach
    public void initialize() {
        aladdin = new Book("1", "Aladdin",
                new Author("1", "Aladdin Author"),
                new Genre("2", "Adventure"));
    }

    @DisplayName("искать все комментарии")
    @Test
    public void shouldHasSizeEqualTwo() {
        assertThat(commentRepository.findAll()).hasSize(3);
    }

    @DisplayName("искать комментарий по book_id")
    @Test
    public void shouldSearchByBookId() {
        List<Comment> byBook = commentRepository.findByBook(aladdin);
        assertThat(byBook).isNotNull();
        assertThat(byBook).hasSize(2);
        for (Comment comment : byBook) {
            assertEquals(comment.getBook().getId(), "1");
        }
    }

    @DisplayName("добавлять новый комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldAddComment() {
        assertThat(commentRepository.findAll()).hasSize(3);
        commentRepository.save(new Comment("Третий коммент Алладину", aladdin));
        assertThat(commentRepository.findAll()).hasSize(4);
    }

    @DisplayName("искать комментарий по id")
    @Test
    public void shouldSearchById() {
        Optional<Comment> optionalComment = commentRepository.findById("1");
        assertTrue(optionalComment.isPresent());
        assertEquals(optionalComment.get().getId(), "1");
        assertEquals(optionalComment.get().getText(), "Комментарий к Алладину");
        assertEquals(optionalComment.get().getBook().getId(), "1");
    }

    @DisplayName("обновлять комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldUpdateComment() {
        Comment comment = new Comment("1", "Изменили коммент", aladdin);
        Comment updated = commentRepository.save(comment);
        assertEquals(updated.getId(), "1");
        assertEquals(updated.getText(), "Изменили коммент");
        assertEquals(updated.getBook().getId(), "1");
    }

    @DisplayName("удалять комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldDeleteComment() {
        assertThat(commentRepository.findAll()).hasSize(3);
        commentRepository.deleteById("1");
        assertThat(commentRepository.findAll()).hasSize(2);
    }
}
