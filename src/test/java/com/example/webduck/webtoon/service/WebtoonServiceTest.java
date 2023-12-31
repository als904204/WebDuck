package com.example.webduck.webtoon.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.webduck.webtoon.dto.WebtoonRequest;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        verify(webtoonRepository, times(1)).findById(id);
    }

    @DisplayName("웹툰 목록 조회")
    @Test
    void findWebtoonList() {
        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title("Webtoon 1").summary("Summary 1").imagePath("Path 1")
                .publishDay(PublishDay.MONDAY).originalImageName("Image1.png").build(),
            Webtoon.builder().title("Webtoon 2").summary("Summary 2").imagePath("Path 2")
                .publishDay(PublishDay.FRIDAY).originalImageName("Image2.png").build(),
            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png").build(),
            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png").build()
        );
        when(webtoonRepository.findAll()).thenReturn(webtoons);

        List<WebtoonRequest> result = webtoonService.findWebtoonList();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(4);
        verify(webtoonRepository, times(1)).findAll();
    }


    @DisplayName("요일별 웹툰 목록 조회")
    @Test
    void findWebtoonsByPublicDay() {
        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png").build(),
            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png").build()
        );

        when(webtoonRepository.findWebtoonByPublishDay(PublishDay.SUNDAY)).thenReturn(webtoons);

        List<WebtoonRequest> webtoonsByPublishDay = webtoonService.findWebtoonByPublishDay(PublishDay.SUNDAY);

        assertThat(webtoonsByPublishDay).isNotNull();
        assertThat(webtoonsByPublishDay).hasSize(2);
    }
}