package ru.kotikov.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для книг должен ")
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    private final BookDto bookDtoWithId =
            new BookDto("1", "Name", "1", "Aladdin Author", "2", "Adventure");

    private final BookDto bookDto =
            new BookDto(null, "Name", "1", "Aladdin Author", "2", "Adventure");

    private final BookWithCommentDto bookWithCommentDto = new BookWithCommentDto(bookDto, new ArrayList<>());

    @Test
    @DisplayName("получать список книг")
    void shouldGetAllReturnOk() throws Exception {
        given(bookService.getAllBooks()).willReturn(List.of(bookDto));
        mvc.perform(get("/api/book/"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookDto))));
    }

    @Test
    @DisplayName("получать книгу по id")
    void shouldGetByIdReturnOk() throws Exception {
        given(bookService.getBookWithCommentsByBookId("1")).willReturn(bookWithCommentDto);

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookWithCommentDto)));
    }

    @Test
    @DisplayName("сохранять книгу")
    void shouldAddReturnOk() throws Exception {
        given(bookService.saveBook(any(BookDto.class))).willReturn(bookDtoWithId);
        mvc.perform(post("/api/book").contentType("application/json")
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditPageReturnOk() throws Exception {
        given(bookService.saveBook(bookDtoWithId)).willReturn(bookDtoWithId);
        mvc.perform(put("/api/book").contentType("application/json")
                        .content(mapper.writeValueAsString(bookDtoWithId)))
                .andExpect(status().isOk());
    }
}
