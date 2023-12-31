package com.example.webduck.webtoon.controller.api;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.webtoon.dto.WebtoonRequest;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.service.WebtoonService;
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
class WebtoonApiControllerTest {


    private MockMvc mockMvc;

    @Mock
    private WebtoonService webtoonService;

    @InjectMocks
    private WebtoonApiController webtoonApiController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webtoonApiController).build();
    }

    private final String url = "/api/v1/webtoon/";

    @DisplayName("id 로 웹툰 조회")
    @Test
    void getWebtoon() throws Exception {
        Long id = 1L;
        WebtoonRequest webtoonRequest = new WebtoonRequest(
            Webtoon.builder()
                .title("Webtoon 1")
                .summary("Summary 1")
                .imagePath("Path 1")
                .publishDay(PublishDay.MONDAY)
                .originalImageName("Image1.png").build()
        );

        when(webtoonService.findWebtoonById(id)).thenReturn(webtoonRequest);

        RequestBuilder reqBuilder = MockMvcRequestBuilders
            .get(url +id)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(reqBuilder)
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("웹툰 목록 조회")
    @Test
    void getWebtoonList() {

    }

    @Test
    void getWebtoonListByPublish() {
    }
}