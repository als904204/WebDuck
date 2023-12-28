package com.example.webduck.admin.service;

import com.example.webduck.Webtoon.dto.WebtoonUpload;
import com.example.webduck.Webtoon.entity.Webtoon;
import com.example.webduck.Webtoon.repository.WebtoonRepository;
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


    //  TODO : Server custom Exception
    public Long uploadWebtoon(WebtoonUpload webtoonUpload) {
        Webtoon savedWebtoon;

        try {
            MultipartFile imageFile = webtoonUpload.getImageFile();
            String originalFileName = imageFile.getOriginalFilename();
            String imagePath = fileStore.upload(imageFile);
            log.info("imagePath={}",imagePath);

            Webtoon webtoon = Webtoon.builder()
                .title(webtoonUpload.getTitle())
                .summary(webtoonUpload.getSummary())
                .publishDay(webtoonUpload.getPublishDay())
                .originalImageName(originalFileName)
                .imagePath(imagePath) // 상대 경로 저장
                .build();

            savedWebtoon = webtoonRepository.save(webtoon);

        } catch (IOException e) {
            log.error("upload #e={}",e.getMessage());
            throw new IllegalArgumentException("Upload Error");
        }
        return savedWebtoon.getId();
    }

}
