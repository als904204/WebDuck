package com.example.webduck.genre.controller.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.genre.dto.GenreResponse;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.service.GenreService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(GenreApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class GenreApiControllerDocsTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenreService genreService;
    private final String uri = "/api/v1/genre";
    private Genre g1,g2;

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();

        g1 = new Genre("로맨스");
        g2 = new Genre("액션");
    }

    @Test
    void findAllGenres() throws Exception {
        List<GenreResponse> mockGenreResponse = List.of(
            new GenreResponse(g1),
            new GenreResponse(g2)
        );

        Mockito.when(genreService.findAllGenres()).thenReturn(mockGenreResponse);

        mockMvc.perform(get(uri))
            .andExpect(status().isOk())

            .andDo(document("get-v1-get-genres",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].genreName").description("장르 이름")
                )
            ));

    }




}