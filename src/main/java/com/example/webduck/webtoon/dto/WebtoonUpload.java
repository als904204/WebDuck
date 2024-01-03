package com.example.webduck.webtoon.dto;

import com.example.webduck.genre.entity.GenreType;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter @Setter
public class WebtoonUpload {
    private String title;
    private String summary;
    private PublishDay publishDay;
    private MultipartFile imageFile;
    private Platform platform;
    private List<GenreType> genreTypes; // 장르 타입 TODO : N+1 아마 터질거임
}

