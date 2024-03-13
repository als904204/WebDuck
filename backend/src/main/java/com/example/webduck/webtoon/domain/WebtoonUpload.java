package com.example.webduck.webtoon.domain;

import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter @Setter
public class WebtoonUpload {

    @NotBlank(message = "제목은 빈칸이 될 수 없습니다")
    @Size(max = 40, message = "제목은 최대 40자까지 가능합니다")
    private String title;

    @NotBlank(message = "줄거리는 빈칸이 될 수 없습니다")
    private String summary;

    @NotNull(message = "요일은 Null이 될 수 없습니다")
    private PublishDay publishDay;

    @NotNull(message = "썸네일 이미지는 Null이 될 수 없습니다")
    private MultipartFile imageFile;

    @NotNull(message = "플랫폼은 Null이 될 수 없습니다")
    private Platform platform;

    @NotEmpty(message = "한가지 이상의 장르를 선택해야 됩니다")
    private List<String> genreName;

    @NotBlank(message = "작가는 빈칸이 될 수 없습니다")
    @Size(max = 15, message = "작가는 최대 30자까지 가능합니다")
    private String author;

}

