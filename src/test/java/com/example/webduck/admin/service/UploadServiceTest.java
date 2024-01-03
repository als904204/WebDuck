package com.example.webduck.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.webduck.admin.service.impl.LocalStore;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.GenreType;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.genre.repository.WebtoonGenreRepository;
import com.example.webduck.webtoon.dto.WebtoonUpload;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UploadServiceTest {

    @Mock
    private WebtoonRepository webtoonRepository;
    @Mock
    private FileStore fileStore;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private WebtoonGenreRepository webtoonGenreRepository;
    @InjectMocks
    private UploadService uploadService;

    private final String title = "마음의소리";
    private final String summary = "줄거리";
    private final String path = "testPath";
    private final String imageName = "imgName";
    private final PublishDay publishDay = PublishDay.FRIDAY;

    private final Platform platform = Platform.NAVER;


    List<GenreType> genreTypes;
    Webtoon webtoon;

    WebtoonUpload webtoonUpload;

    @Test
    void uploadWebtoonTest() throws IOException {
        MultipartFile mockMultipartFile = new MockMultipartFile(
            "hello",
            "originalFileName",
            "image/png",
            "asdf".getBytes());

        webtoonUpload = new WebtoonUpload(
            title,
            summary,
            publishDay,
            mockMultipartFile,
            platform,
            genreTypes
        );

        webtoon = Webtoon.builder()
            .title(webtoonUpload.getTitle())
            .summary(webtoonUpload.getSummary())
            .imagePath(path)
            .publishDay(webtoonUpload.getPublishDay())
            .platform(webtoonUpload.getPlatform())
            .originalImageName(imageName)
            .build();

        given(fileStore.upload(mockMultipartFile)).willReturn(path);

        for (GenreType genreType : genreTypes) {
            given(genreRepository.findByGenreType(genreType)).willReturn(Optional.of(new Genre(genreType)));
        }

        uploadService.uploadWebtoon(webtoonUpload);
        verify(fileStore).upload(any(MultipartFile.class));
        verify(webtoonRepository).save(any(Webtoon.class));
        verify(genreRepository, times(webtoonUpload.getGenreTypes().size())).findByGenreType(any(GenreType.class));
        verify(webtoonGenreRepository, times(webtoonUpload.getGenreTypes().size())).save(any(
            WebtoonGenre.class));
    }


}