package com.example.webduck.admin.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.admin.service.UploadService;
import com.example.webduck.webtoon.dto.WebtoonUpload;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AdminApiControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AdminApiController adminApiController;
    @Mock
    private UploadService uploadService;

    private final String title = "제목";
    private final String summary = "줄거리";
    private final String author = "작가";
    private final PublishDay publishDay = PublishDay.SUNDAY;

    private final Platform platform = Platform.NAVER;

    private final List<String> genres = List.of("ROMANCE", "FANTASY");

    private final String uri = "/api/v1/admin/";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminApiController).build();
    }

    @DisplayName("어드민 웹툰 업로드 성공")
    @Test
    void adminWebtoonUploadSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "imageFile", // dto 필드명과 일치 해야 함
            "originalFileName.png",
            "image/png",
            "image content".getBytes());

        WebtoonUpload webtoonUpload = new WebtoonUpload(
            title,
            summary,
            publishDay,
            file,
            platform,
            genres,
            author
        );
        doNothing().when(uploadService).uploadWebtoon(any(WebtoonUpload.class));

        mockMvc.perform(MockMvcRequestBuilders.multipart(uri + "webtoon")
                .file(file)
                .param("title", title)
                .param("summary", summary)
                .param("publishDay", publishDay.name())
                .param("platform", platform.name())
                .param("genreName", genres.toArray(new String[0]))
                .param("author", author)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
            .andExpect(status().isNoContent());
    }

    @DisplayName("어드민 웹툰 업로드 실패 - 잘못된 값(공백,제목 40자이상)")
    @Test
    void adminWebtoonUploadFail() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "imageFile", // dto 필드명과 일치 해야 함
            "originalFileName.png",
            "image/png",
            "image content".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart(uri + "webtoon")
                .file(file)
                .param("title", "")
                .param("summary", summary)
                .param("publishDay", publishDay.name())
                .param("platform", platform.name())
                .param("genreName", genres.toArray(new String[0]))
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
            .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.multipart(uri + "webtoon")
                .file(file)
                .param("title", title)
                .param("summary", summary)
                .param("publishDay",publishDay.name())
                .param("platform", platform.name())
                .param("genreName", "")
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
            .andExpect(status().isBadRequest());

        final String moreThen40 = "ffskdkdkdkdkdkdkdkkdkdkdddddddddffdfdfdfdfdfdfdkdkdkdkkdkdkdkdkdkdddd";
        mockMvc.perform(MockMvcRequestBuilders.multipart(uri + "webtoon")
                .file(file)
                .param("title", moreThen40)
                .param("summary", summary)
                .param("publishDay", "")
                .param("platform", platform.name())
                .param("genreName", genres.toArray(new String[0]))
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }))
            .andExpect(status().isBadRequest());
    }
}