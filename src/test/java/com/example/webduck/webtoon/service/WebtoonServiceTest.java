package com.example.webduck.webtoon.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.webduck.webtoon.dto.WebtoonRequest;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class WebtoonServiceTest {

    @InjectMocks
    private WebtoonService webtoonService;
    @Mock
    private WebtoonRepository webtoonRepository;

    private final String title = "Webtoon 1";
    private final String summary = "Summary 1";
    private final String path = "Path 1";
    private final String imageName = "Image1.png";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("id로 웹툰 조회")
    @Test
    void findWebtoonById() {
        Webtoon webtoon = Webtoon.builder()
            .title(title)
            .summary(summary)
            .imagePath(path)
            .publishDay(PublishDay.MONDAY)
            .originalImageName(imageName)
            .build();

        Long id = 1L;
        when(webtoonRepository.findById(id)).thenReturn(Optional.of(webtoon));

        WebtoonRequest result = webtoonService.findWebtoonById(id);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Webtoon 1");
        assertThat(result.getSummary()).isEqualTo("Summary 1");
        assertThat(result.getPublishDay()).isEqualTo(PublishDay.MONDAY);
        assertThat(result.getImagePath()).isEqualTo("Path 1");
        assertThat(result.getOriginalImageName()).isEqualTo("Image1.png");
    }
}