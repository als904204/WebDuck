package com.example.webduck.genre.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.genre.dto.GenreResponse;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.service.GenreService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class GenreApiControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private GenreApiController genreApiController;
    @Mock
    private GenreService genreService;

    private final String uri = "/api/v1/genre";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(genreApiController).build();
    }

    @DisplayName("장르 목록 조회")
    @Test
    void getGenreList() throws Exception {
        List<GenreResponse> genreResponseList = List.of(
            new GenreResponse(new Genre("무협")),
            new GenreResponse(new Genre("로맨스")),
            new GenreResponse(new Genre("성인"))
        );

        when(genreService.findAllGenres()).thenReturn(genreResponseList);

        RequestBuilder reqBuilder = MockMvcRequestBuilders
            .get(uri)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(reqBuilder)
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("장르 저장")
    @Test
    void saveGenre() throws Exception{

        final String type = "무협";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .param("type", type);

        mockMvc.perform(requestBuilder)
            .andExpect(status().isNoContent())
            .andDo(print());
    }
}