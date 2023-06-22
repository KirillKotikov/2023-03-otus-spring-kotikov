package ru.kotikov.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kotikov.library.dtos.BookDto;
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
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("получать книгу по id")
    void shouldGetByIdReturnOk() throws Exception {
        given(bookService.getBookById("1")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(get("/get-by-id").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять книгу")
    void shouldAddReturnOk() throws Exception {
        mvc.perform(get("/add"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditPageReturnOk() throws Exception {
        given(bookService.getBookById("1")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("сохранять книгу")
    void shouldSaveReturnRedirect() throws Exception {
        given(bookService.saveBook(null, "Name", "1",
                "2")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(post("/edit")
                        .requestAttr("book",
                                new BookDto(null, "Name", "1",
                                        null, "2", null)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldDeleteBookReturnRedirect() throws Exception {
        mvc.perform(post("/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("удалять комментарий")
    void shouldDeleteCommentReturnOk() throws Exception {
        given(commentService.getByBookId("1")).willReturn(new ArrayList<>());
        given(bookService.getBookById("1")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        mvc.perform(get("/comment/delete?commentId=1&bookId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять комментарий")
    void shouldAddCommentReturnOk() throws Exception {
        given(bookService.getBookById("1")).willReturn(new BookDto(null, "Name", "1",
                null, "2", null));
        given(commentService.getByBookId("1")).willReturn(new ArrayList<>());
        mvc.perform(post("/comment/add?bookId=1")
                        .requestAttr("text", "text"))
                .andExpect(status().isOk());
    }
}
