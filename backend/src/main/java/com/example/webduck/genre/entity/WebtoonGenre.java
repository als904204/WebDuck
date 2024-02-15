package com.example.webduck.genre.entity;

import com.example.webduck.genre.entity.Genre;
import com.example.webduck.webtoon.entity.Webtoon;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 웹툰 - 장르 테이블을 연결시켜주는 중간 테이블
 * WHY? 하나의 웹툰은 여러개의 장르가 포함될 수 있고, 하나의 장르에는 여러개의 웹툰이 포함 될 수 있다.
 * 즉 N-N 관계
 */
@Entity
public class WebtoonGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Webtoon getWebtoon() {
        return webtoon;
    }

    public Genre getGenre() {
        return genre;
    }

    // 양방향 참조 설정
    public void setWebtoon(Webtoon webtoon) {
        this.webtoon = webtoon;
        if (!webtoon.getWebtoonGenres().contains(this)) {
            webtoon.getWebtoonGenres().add(this);
        }
    }

    // 현재 단방향임
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
