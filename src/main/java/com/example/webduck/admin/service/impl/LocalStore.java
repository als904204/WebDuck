package com.example.webduck.admin.service.impl;

import static com.example.webduck.global.exception.exceptionCode.LogicExceptionCode.BAD_REQUEST;
import static com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode.INVALID_FILE_TYPE;

import com.example.webduck.admin.service.FileStore;
import com.example.webduck.global.exception.CustomException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * file upload to local
 */
@Slf4j
@Component
public class LocalStore implements FileStore {
    private static final String[] ALLOWED_CONTENT_TYPES = {
        "image/jpeg",
        "image/jpg",
        "image/png",
    };

    private final String UPLOAD_DIR = System.getProperty("user.home") + "/Desktop/img/";


    @Override
    public String upload(MultipartFile file) throws IOException {

        // check null or empty
        if (file == null || file.isEmpty()) {
            log.error("File is null or empty");
            throw new CustomException(BAD_REQUEST);
        }

        String contentType = file.getContentType();

        if (contentType == null || !Arrays.asList(ALLOWED_CONTENT_TYPES).contains(contentType)) {
            log.error("Invalid file type={}",contentType);
            throw new CustomException(INVALID_FILE_TYPE);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 바탕화면/img 로 경로 설정
        Path storageDirectory = Paths.get(UPLOAD_DIR);

        if (!Files.exists(storageDirectory)) {
            log.warn("no dir /desktop/img");
            Files.createDirectories(storageDirectory);
        }

        // 바탕화면/img 폴더에 파일 업로드
        Path destinationPath = storageDirectory.resolve(filename);
        file.transferTo(destinationPath.toFile());

        return "/images/" + filename;
    }


}
