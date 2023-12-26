package com.example.webduck.util.upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class ImageUploadUtil {


    // TODO : Exception & Upload to S3
    public static String saveImageToFileSystem(MultipartFile image) throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/static/temp";

        if (image != null && !image.isEmpty()) {
            // 중복 파일명 처리를 위한 UUID
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path destinationFilePath = Paths.get(path, filename);
            Files.copy(image.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            return destinationFilePath.toString();
        }
        throw new IllegalArgumentException("File Upload Exception!!");
    }




}
