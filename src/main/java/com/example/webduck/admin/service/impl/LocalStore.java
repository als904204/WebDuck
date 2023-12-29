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


        String path = getLocalPath();
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationFilePath = Paths.get(path, filename);

        Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        return getRelativePath(destinationFilePath);
    }

    // 로컬 저장 경로 반환 (User/KIM/Project/src/main...)
    private String getLocalPath() {
        String path = System.getProperty("user.dir") + "/src/main/resources/static/temp";
        File folder = new File(path);

        if (!folder.exists()) {
            log.warn("Create a local directory because it doesn't exist.");
            boolean isCreated = folder.mkdirs();
            log.info("directory is created={}",isCreated);
        }
        return path;
    }

    // 저장된 파일의 상대 경로 반환 (/temp/webtoonImage1.png)
    private String getRelativePath(Path destinationFilePath) {
        String imagePath = destinationFilePath.toString();

        return "/static/temp/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);
    }

}
