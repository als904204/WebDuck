package com.example.webduck.admin.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.global.exception.CustomException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class LocalStoreTest {


    private LocalStore localStore;

    @BeforeEach
    void setUp() {
        localStore = new LocalStore();
    }

    @DisplayName("업로드 jpg 성공")
    @Test
    void whenFileUpload_jpg_success() throws IOException {

        // given
        MockMultipartFile jpgFile = new MockMultipartFile(
            "File",
            "hello.jpg",
            "image/jpg", new byte[1024]);

        // when
        String uploadedPath = localStore.upload(jpgFile);

        // then
        Assertions.assertThat(uploadedPath).isNotNull();

        Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
            uploadedPath);

        Files.deleteIfExists(filePath);
    }

    @DisplayName("업로드 png 성공")
    @Test
    void whenFileUpload_png_success() throws IOException {

        // given
        MockMultipartFile jpgFile = new MockMultipartFile(
            "File",
            "hello.png",
            "image/png", new byte[1024]);

        // when
        String uploadedPath = localStore.upload(jpgFile);

        // then
        Assertions.assertThat(uploadedPath).isNotNull();

        Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
            uploadedPath);

        Files.deleteIfExists(filePath);
    }

    @DisplayName("업로드 jpeg 성공")
    @Test
    void whenFileUpload_jpeg_success() throws IOException {

        // given
        MockMultipartFile jpgFile = new MockMultipartFile(
            "File",
            "hello.jpeg",
            "image/jpeg", new byte[1024]);

        // when
        String uploadedPath = localStore.upload(jpgFile);

        // then
        Assertions.assertThat(uploadedPath).isNotNull();

        Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
            uploadedPath);

        Files.deleteIfExists(filePath);
    }

    @DisplayName("업로드 File이 Null이면 예외 발생")
    @Test
    void whenFileIsNull_ThenThrow_BAD_REQUEST() {
        assertThrows(CustomException.class, () -> localStore.upload(null));
    }

    @DisplayName("업로드 File이 Empty이면 예외 발생")
    @Test
    void whenFileIsEmpty_ThenThrow_BAD_REQUEST() {
        assertThrows(CustomException.class, () -> localStore.upload(new MockMultipartFile("emptyFile","empty.png","image/png",new byte[0])));
    }

    @DisplayName("업로드 File 타입 지원 X, 예외 발생")
    @Test
    void whenFileTypeIsValid_ThenThrow_INVALID_FILE_TYPE() {
        MockMultipartFile file = new MockMultipartFile("FILE", "test.txt", "text/plain",
            "TEXT FILE!".getBytes());
        assertThrows(CustomException.class, () -> localStore.upload(file));
    }

}