package com.example.webduck.admin.service.impl;

import com.example.webduck.admin.service.FileStore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LocalStore implements FileStore {


    // todo 커스텀 예외
    @Override
    public String upload(MultipartFile file) throws IOException {
        String path = getLocalPath();
        if (file != null && !file.isEmpty()) {
            // 중복 파일명 처리를 위한 UUID
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destinationFilePath = Paths.get(path, filename);
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            return destinationFilePath.toString();
        }
        throw new IllegalArgumentException("Local file upload exception!!");
    }

    private String getLocalPath() {
        return System.getProperty("user.dir") + "/src/main/resources/static/temp";
    }
}
