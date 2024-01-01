package com.example.webduck.admin.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.dto.WebtoonUpload;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class UploadService {

    private final WebtoonRepository webtoonRepository;
    private final FileStore fileStore;


    public Long uploadWebtoon(WebtoonUpload webtoonUpload) {
        Webtoon savedWebtoon;

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
                .imagePath(imagePath) // 상대 경로 저장         // 이미지 경로
                .platform(webtoonUpload.getPlatform())      // 플랫폼
                .build();

            savedWebtoon = webtoonRepository.save(webtoon);

        } catch (IOException e) {
            log.error("upload #e={}",e.getMessage());
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
        return savedWebtoon.getId();
    }

}
