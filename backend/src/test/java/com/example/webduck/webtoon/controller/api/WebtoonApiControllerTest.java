package com.example.webduck.webtoon.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.customMock.WithMockCustomUser;
import com.example.webduck.webtoon.controller.WebtoonApiController;
import com.example.webduck.webtoon.service.WebtoonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WithMockCustomUser
@WebMvcTest(WebtoonApiController.class)
class WebtoonApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebtoonService webtoonService;

    private final String uri = "/api/v1/webtoon";

    @DisplayName("실패 : 잘못된 publishDay 요청")
    @Test
    void testInvalidPublishDayRequest() throws Exception {
        var invalidReq = "VNZNVNNE";

        mockMvc.perform(get(uri + "/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .param("publishDay", invalidReq))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("실패 : 잘못된 platform 요청")
    @Test
    void testInvalidPlatformRequest() throws Exception {
        var invalidReq = "ASDFVZCXV";

        mockMvc.perform(get(uri + "/platform")
                .contentType(MediaType.APPLICATION_JSON)
                .param("platform", invalidReq))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("실패 : 잘못된 sortBy 요청")
    @Test
    void testInvalidSortBy() throws Exception {
        var invalidSortBy = "hahahahahhh";

        when(webtoonService.findWebtoonsByWebtoonCondition(invalidSortBy))
            .thenThrow(new CustomException(LogicExceptionCode.BAD_REQUEST));

        mockMvc.perform(get(uri + "/popular")
                .contentType(MediaType.APPLICATION_JSON)
                .param("sortBy", invalidSortBy))
            .andExpect(status().isBadRequest());
    }


    @DisplayName("실패 : 잘못된 웹툰 ID 요청")
    @Test
    void testInvalidWebtoonId() throws Exception {
        var invalidWebtoonId = 1231L;
        when(webtoonService.getWebtoonDetails(invalidWebtoonId))
            .thenThrow(new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND));

        mockMvc.perform(get(uri + "/{id}", invalidWebtoonId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}