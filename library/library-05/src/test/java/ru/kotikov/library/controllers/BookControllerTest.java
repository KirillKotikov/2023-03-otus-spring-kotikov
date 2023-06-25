package ru.kotikov.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.CommentService;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// У меня не получается тестировать с @WebMvcTest. Выходят ошибки из-за Монги.
//@WebMvcTest(BookController.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для книг должен ")
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("получать список книг")
    void shouldGetAllReturnOk() throws Exception {
        mvc.perform(get("/api/book"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("получать книгу по id")
    void shouldGetByIdReturnOk() throws Exception {
        given(bookService.getBookWithCommentsByBookId("1")).willReturn(new BookWithCommentDto(
                new BookDto(null, "Name", "1", null, "2", null),
                new ArrayList<>()));
        mvc.perform(get("/api/book/").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять книгу")
    void shouldAddReturnOk() throws Exception {
        mvc.perform(get("/api/book/addingPage"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditPageReturnOk() throws Exception {
        given(bookService.getBookById("1")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(get("/api/book/editPage/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("сохранять книгу")
    void shouldSaveReturnRedirect() throws Exception {
        given(bookService.saveBook(new BookDto(null, "Name", "1", null,
                "2", null))).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(post("/api/book")
                        .requestAttr("book",
                                new BookDto(null, "Name", "1",
                                        null, "2", null)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldDeleteBookReturnRedirect() throws Exception {
        mvc.perform(post("/api/book/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("удалять комментарий")
    void shouldDeleteCommentReturnOk() throws Exception {
        given(bookService.getBookWithCommentsByBookId("1")).willReturn(new BookWithCommentDto(
                new BookDto(null, "Name", "1", null, "2", null),
                new ArrayList<>()));
        mvc.perform(post("/api/book/1/comment/delete?commentId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять комментарий")
    void shouldAddCommentReturnOk() throws Exception {
        given(bookService.getBookWithCommentsByBookId("1")).willReturn(new BookWithCommentDto(
                new BookDto(null, "Name", "1", null, "2", null),
                new ArrayList<>()));
        mvc.perform(post("/api/book/1/comment")
                        .requestAttr("text", "text"))
                .andExpect(status().isOk());
    }
}
