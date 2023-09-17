package ru.kotikov.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.dtos.CommentDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Сервис книг должен ")
@SpringBootTest
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("искать все книги")
    @Test
    public void shouldShowAllBooks() {
        assertThat(bookService.getAllBooks()).hasSize(3);
    }

    @DisplayName("найти книгу по id с комментариями")
    @Test
    public void shouldFoundBookByIdWithComments() {
        BookWithCommentDto bookWithCommentDto = bookService.getBookWithCommentsByBookId("1");
        BookDto bookDto = bookWithCommentDto.getBookDto();
        List<CommentDto> commentDtoList = bookWithCommentDto.getCommentDtoList();
        assertEquals(bookDto.getName(), "Aladdin");
        assertEquals(bookDto.getAuthorId(), "1");
        assertEquals(bookDto.getGenreId(), "2");
        assertThat(commentDtoList).hasSize(2);
    }

}
