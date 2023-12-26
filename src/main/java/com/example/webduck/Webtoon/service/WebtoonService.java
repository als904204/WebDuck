package com.example.webduck.Webtoon.service;

import com.example.webduck.Webtoon.dto.WebtoonUploadDto;
import com.example.webduck.Webtoon.entity.Webtoon;
import com.example.webduck.Webtoon.repository.WebtoonRepository;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;

    // TODO : Exception, Move to Admin Service
    public Long uploadWebtoon(WebtoonUploadDto webtoonUploadDto) {
        Webtoon savedWebtoon;
        try {
            MultipartFile imageFile = webtoonUploadDto.getImageFile();
            String originalFileName = imageFile.getOriginalFilename();
            String imagePath = ImageUpload.saveImageToFileSystem(imageFile);

            log.info("originalFileName={}",originalFileName);
            log.info("image={}",imagePath);

            // 데이터베이스에 저장할 상대 경로
            String relativePath = "/temp/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);

            Webtoon webtoon = Webtoon.builder()
                .title(webtoonUploadDto.getTitle())
                .summary(webtoonUploadDto.getSummary())
                .originalImageName(originalFileName)
                .imagePath(relativePath) // 상대 경로 저장
                .build();

            savedWebtoon = webtoonRepository.save(webtoon);

        } catch (IOException e) {
            log.error("upload #e={}",e.getMessage());
            throw new IllegalArgumentException("Upload Error");
        }
        return savedWebtoon.getId();
    }


}
