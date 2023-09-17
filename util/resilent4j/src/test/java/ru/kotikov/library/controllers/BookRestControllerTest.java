package ru.kotikov.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.services.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для книг должен ")
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private final BookDto bookDto =
            new BookDto(null, "Name", "1", "Aladdin Author", "2", "Adventure");

    private final BookWithCommentDto bookWithCommentDto = new BookWithCommentDto(bookDto, new ArrayList<>());

    @Test
    @DisplayName("получать список книг")
    void shouldGetAllReturnOk() throws Exception {
        given(bookService.getAllBooks()).willReturn(List.of(bookDto));
        MvcResult mvcResult = mvc.perform(get("/api/book/"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(mvcResult.getAsyncResult(), List.of(bookDto));
    }

    @Test
    @DisplayName("получать книгу по id")
    void shouldGetByIdReturnOk() throws Exception {
        given(bookService.getBookWithCommentsByBookId("1")).willReturn(bookWithCommentDto);

        MvcResult mvcResult = mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk()).andReturn();
        assertEquals(mvcResult.getAsyncResult(), bookWithCommentDto);
    }

}
