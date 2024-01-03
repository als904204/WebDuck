package com.example.webduck.admin.service;

import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.genre.repository.WebtoonGenreRepository;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode;
import com.example.webduck.webtoon.dto.WebtoonUpload;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class UploadService {

    private final WebtoonRepository webtoonRepository;
    private final FileStore fileStore;
    private final GenreRepository genreRepository;
    private final WebtoonGenreRepository webtoonGenreRepository;


    @Transactional
    public void uploadWebtoon(WebtoonUpload webtoonUpload) {
        try {
            MultipartFile imageFile = webtoonUpload.getImageFile();
            String originalFileName = imageFile.getOriginalFilename();
            String imagePath = fileStore.upload(imageFile);
            log.info("imagePath={}",imagePath);

            Webtoon webtoon = Webtoon.builder()
                .title(webtoonUpload.getTitle())            // 제목
                .summary(webtoonUpload.getSummary())        // 줄거리
                .publishDay(webtoonUpload.getPublishDay())  // 요일
                .originalImageName(originalFileName)        // 이미지 원본이름
                .imagePath(imagePath)                       // 이미지 경로
                .platform(webtoonUpload.getPlatform())      // 플랫폼
                .build();

            webtoonRepository.save(webtoon);

            // (WebtoonGenre<=>Webtoon) 양방향 참조 연관관계 설정
            // (WebtoonGenre==>Genre) 단방향 참조 설정
            for (String genreType : webtoonUpload.getGenreType()) {
                Genre genre = genreRepository.findByType(genreType)
                    .orElseThrow(() -> new CustomException(ValidationExceptionCode.INVALID_GENRE_TYPE));

                WebtoonGenre webtoonGenre = new WebtoonGenre();
                webtoonGenre.setGenre(genre);
                webtoonGenre.setWebtoon(webtoon);

                webtoon.addWebtoonGenre(webtoonGenre);

                webtoonGenreRepository.save(webtoonGenre);
            }
        } catch (IOException e) {
            log.error("upload #e={}",e.getMessage());
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
    }

}
