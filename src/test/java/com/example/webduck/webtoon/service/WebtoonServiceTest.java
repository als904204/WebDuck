package com.example.webduck.webtoon.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
            .platform(Platform.NAVER)
            .originalImageName(imageName)
            .build();

        Long id = 1L;
        when(webtoonRepository.findById(id)).thenReturn(Optional.of(webtoon));

        WebtoonResponse result = webtoonService.findWebtoonById(id);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Webtoon 1");
        assertThat(result.getSummary()).isEqualTo("Summary 1");
        assertThat(result.getPublishDay()).isEqualTo(PublishDay.MONDAY);
        assertThat(result.getPlatform()).isEqualTo(Platform.NAVER);
        assertThat(result.getImagePath()).isEqualTo("Path 1");
        assertThat(result.getOriginalImageName()).isEqualTo("Image1.png");

        verify(webtoonRepository, times(1)).findById(id);
    }

    @DisplayName("웹툰 목록 조회")
    @Test
    void findWebtoonList() {
        // given
        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title("Webtoon 1").summary("Summary 1").imagePath("Path 1")
                .publishDay(PublishDay.MONDAY).originalImageName("Image1.png").platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 2").summary("Summary 2").imagePath("Path 2")
                .publishDay(PublishDay.FRIDAY).originalImageName("Image2.png").platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png").platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png").platform(Platform.NAVER).build()
        );

        // when
        when(webtoonRepository.findAll()).thenReturn(webtoons);

        // then
        List<WebtoonResponse> result = webtoonService.findWebtoonList();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(4);

        WebtoonResponse webtoonResponse = result.get(0);
        assertThat(webtoonResponse.getTitle()).isEqualTo("Webtoon 1");
        assertThat(webtoonResponse.getSummary()).isEqualTo("Summary 1");
        assertThat(webtoonResponse.getImagePath()).isEqualTo("Path 1");
        assertThat(webtoonResponse.getPublishDay()).isEqualTo(PublishDay.MONDAY);
        assertThat(webtoonResponse.getOriginalImageName()).isEqualTo("Image1.png");
        assertThat(webtoonResponse.getPlatform()).isEqualTo(Platform.NAVER);

        verify(webtoonRepository, times(1)).findAll();
    }


    @DisplayName("요일별 웹툰 목록 조회")
    @Test
    void findWebtoonsByPublicDay() {
        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png").platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png").platform(Platform.NAVER).build()
        );

        when(webtoonRepository.findWebtoonsByPublishDay(PublishDay.SUNDAY)).thenReturn(webtoons);

        List<WebtoonResponse> webtoonsByPublishDay = webtoonService.findWebtoonsByPublishDay(PublishDay.SUNDAY);

        assertThat(webtoonsByPublishDay).isNotNull();
        assertThat(webtoonsByPublishDay).hasSize(2);

        Assertions.assertThat(webtoonsByPublishDay)
            .allMatch(mondayWebtoon -> mondayWebtoon.getPublishDay() == PublishDay.SUNDAY);

    }

    @DisplayName("플랫폼별 웹툰 목록 조회")
    @Test
    void findWebtoonsByPlatform() {
        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png").platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png").platform(Platform.NAVER).build()
        );

        when(webtoonRepository.findWebtoonsByPlatform(Platform.NAVER)).thenReturn(webtoons);

        List<WebtoonResponse> webtoonsByPlatform = webtoonService.findWebtoonsByPlatform(Platform.NAVER);

        assertThat(webtoonsByPlatform).isNotNull();
        assertThat(webtoonsByPlatform).hasSize(2);

        Assertions.assertThat(webtoonsByPlatform)
            .allMatch(mondayWebtoon -> mondayWebtoon.getPlatform() == Platform.NAVER);
    }

    @DisplayName("장르별 웹툰 목록 조회")
    @Test
    void findWebtoonsByGenre() {
        final String romance = "로맨스";

        List<Webtoon> webtoons = List.of(
            Webtoon.builder().title(romance).summary("Summary 3").imagePath("Path 3")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png")
                .platform(Platform.NAVER).build(),

            Webtoon.builder().title(romance).summary("Summary 4").imagePath("Path 4")
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png")
                .platform(Platform.NAVER).build()
        );

        when(webtoonRepository.findWebtoonsByGenreName(romance)).thenReturn(webtoons);

        List<WebtoonResponse> foundWebtoonsByGenre = webtoonService.findWebtoonsByGenreName(romance);

        assertThat(foundWebtoonsByGenre).isNotNull();
        assertThat(foundWebtoonsByGenre).hasSize(webtoons.size());
        assertThat(foundWebtoonsByGenre).hasSize(webtoons.size());

        assertThat(foundWebtoonsByGenre.get(0).getTitle()).isEqualTo(romance);
        assertThat(foundWebtoonsByGenre).allMatch(
            romanceWebtoon -> romanceWebtoon.getTitle().equals(romance));

    }

    @DisplayName("장르필터별 웹툰 목록 조회")
    @Test
    void findWebtoonsByGenreNames() {
        final String romance = "로맨스";
        final String gag = "개그";

        List<String> requestGenres = Arrays.asList(romance, gag);

        List<WebtoonGenreResponse> responses = List.of(
            new WebtoonGenreResponse(1L, romance, "imgPath", "originaImgName"),
            new WebtoonGenreResponse(2L, gag, "imgPath", "originaImgName")
        );

        when(webtoonRepository.findWebtoonsByGenres(requestGenres)).thenReturn(responses);

        List<WebtoonGenreResponse> foundWebtoonsByGenreNames = webtoonService.findWebtoonsByGenreNames(
            requestGenres);

        assertThat(foundWebtoonsByGenreNames).isNotNull();
        assertThat(foundWebtoonsByGenreNames).hasSize(requestGenres.size());
        assertThat(foundWebtoonsByGenreNames.get(0).getTitle()).isEqualTo(romance);
    }


}