package com.example.webduck.admin.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStore {
    String upload(MultipartFile file) throws IOException;

}
