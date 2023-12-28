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

            // 파일 저장 경로 설정
            Path destinationFilePath = Paths.get(path, filename);

            // 파일 복사 및 저장
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 파일의 상대 경로 반환
            return getRelativePath(destinationFilePath);
        }
        throw new IllegalArgumentException("Local file upload exception!!");
    }

    // 로컬 저장 경로 반환 (User/KIM/Project/src/main...)
    private String getLocalPath() {
        return System.getProperty("user.dir") + "/src/main/resources/static/temp";
    }

    // 저장된 파일의 상대 경로 반환 (/temp/webtoonImage1.png)
    private String getRelativePath(Path destinationFilePath) {
        String imagePath = destinationFilePath.toString();

        // static/temp
        return "/temp/" + imagePath.substring(imagePath.lastIndexOf("/") + 1);
    }

}
