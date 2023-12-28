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

// 로컬 환경 파일 업로드
@Component
public class LocalStore implements FileStore {


    @Override
    public String upload(MultipartFile file) throws IOException {
        String path = getLocalPath();
        if (file != null && !file.isEmpty()) {
            // 중복 파일명 처리를 위한 UUID
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path destinationFilePath = Paths.get(path, filename);
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            return getRelativePath(destinationFilePath);
        }
        throw new IllegalArgumentException("Local file upload exception!!");
    }

    private String getLocalPath() {
        return System.getProperty("user.dir") + "/src/main/resources/static/temp";
    }

    private String getRelativePath(Path destinationFilePath) {
        String imagePath = destinationFilePath.toString();

        // static/temp
        return "/temp/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);
    }

}
