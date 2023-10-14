package ru.kotikov.blog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.kotikov.blog.Exceptions.DataNotFoundException;
import ru.kotikov.blog.dtos.CommentDto;
import ru.kotikov.blog.services.CommentService;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(
        username = "admin",
        password = "admin",
        roles = {"USER", "ADMIN"}
)
@DisplayName("Контроллер для комментариев должен ")
public class CommentRestControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    private static CommentDto commentDtoWithId;

    private static CommentDto commentDtoWithoutId;

    @BeforeEach
    void beforeEach() {
        commentDtoWithId = CommentDto.builder()
                .id("1")
                .postId("1")
                .userNickName("testNick")
                .text("testText")
                .userLogin("testLogin")
                .build();

        commentDtoWithoutId = CommentDto.builder()
                .postId("1")
                .userNickName("testNick")
                .text("testText")
                .userLogin("testLogin")
                .build();
    }

    @Test
    @DisplayName("получать список комментариев по идентификатору поста.")
    void getCommentListByPostId_oneCommentInRepoForFirstPost_returnOneComment()
            throws Exception {
        BDDMockito.given(commentService.getCommentListByPostId("1"))
                .willReturn(List.of(commentDtoWithId));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1/comment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(List.of(commentDtoWithId))));
    }

    @Test
    @DisplayName("выбрасывать ошибку, если такого поста не существует.")
    void getCommentListByPostId_postNotExists_throwsDataNotFoundException()
            throws Exception {
        String error = String.format("%s with %s = \"%s\" is not exists!",
                "Post", "id", "1");
        BDDMockito.given(commentService.getCommentListByPostId("1"))
                .willThrow(new DataNotFoundException(error));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1/comment"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(error));
    }

    @Test
    @DisplayName("получать список комментариев по идентификатору поста и логина пользователя.")
    void getCommentListByPostIdAndLogin_oneCommentInRepoForFirstPost_returnOneComment()
            throws Exception {
        BDDMockito.given(commentService
                .getCommentListByPostIdAndUserLogin("1", "login")
        ).willReturn(List.of(commentDtoWithId));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1/comment/user/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(List.of(commentDtoWithId))));
    }

    @Test
    @DisplayName("выбрасывать ошибку, если такого поста не существует.")
    void getCommentListByPostIdAndLogin_postNotExists_throwsDataNotFoundException()
            throws Exception {
        String error = String.format("%s with %s = \"%s\" is not exists!",
                "Post", "id", "1");
        BDDMockito.given(commentService
                .getCommentListByPostIdAndUserLogin("1", "login")
        ).willThrow(new DataNotFoundException(error));
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1/comment/user/login"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(error));
    }

    @Test
    @DisplayName("Сохранять комментарий.")
    void addComment_correctInputComment_returnCommentDtoWithId()
            throws Exception {
        BDDMockito.given(commentService.addComment(Mockito.any()))
                .willReturn(commentDtoWithId);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/comment")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDtoWithoutId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(commentDtoWithId)));

        Mockito.verify(commentService, Mockito.times(1))
                .addComment(Mockito.any());

    }

    @Test
    @DisplayName("выбрасывать ошибку, если поста, в который пытаемся сохранить комментарий, не существует.")
    void addComment_postNotExists_throwsDataNotFoundException()
            throws Exception {

        String error = String.format("%s with %s = \"%s\" is not exists!",
                "Post", "id", "1");
        BDDMockito.given(commentService.addComment(Mockito.any()))
                .willThrow(new DataNotFoundException(error));

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/comment")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDtoWithoutId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(error));
    }

    @Test
    @DisplayName("обновлять комментарий.")
    void updateComment_correctInputComment_returnCommentDtoWithId()
            throws Exception {
        BDDMockito.given(commentService.updateComment(Mockito.any()))
                .willReturn(commentDtoWithId);
        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/comment")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commentDtoWithoutId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(commentDtoWithId)));

        Mockito.verify(commentService, Mockito.times(1))
                .updateComment(Mockito.any());

    }

    @Test
    @DisplayName("удалять комментарий.")
    void deleteComment_correctInputId_returnNoContent()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/comment/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(commentService, Mockito.times(1))
                .deleteComment(Mockito.any());
    }

}
