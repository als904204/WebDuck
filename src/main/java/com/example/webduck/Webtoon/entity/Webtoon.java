package com.example.webduck.Webtoon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Webtoon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 제목
    private String title;
    // 줄거리
    private String summary;
    // 파일의 원본 이름을 저장하면 나중에 관리자가 해당 파일을 식별하고 검색하는 용이
    private String originalImageName;
    // 썸네일 이미지 경로
    private String imagePath;
    // 웹툰 요일
    @Enumerated(EnumType.STRING)
    private PublishDay publishDay;

    @Builder
    public Webtoon(String title, String summary,String originalImageName, String imagePath,PublishDay publishDay) {
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
    }


}
