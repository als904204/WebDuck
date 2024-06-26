package com.example.webduck.genre.entity;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 웹툰 - 장르 테이블을 연결시켜주는 중간 테이블
 * WHY? 하나의 웹툰은 여러개의 장르가 포함될 수 있고, 하나의 장르에는 여러개의 웹툰이 포함 될 수 있다.
 * 즉 N-N 관계
 */
@Entity
public class WebtoonGenre extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private WebtoonEntity webtoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public WebtoonEntity getWebtoon() {
        return webtoon;
    }

    public Genre getGenre() {
        return genre;
    }

    // 양방향 참조 설정
    public void setWebtoon(WebtoonEntity webtoonEntity) {
        this.webtoon = webtoonEntity;
        if (!webtoonEntity.getWebtoonGenres().contains(this)) {
            webtoonEntity.getWebtoonGenres().add(this);
        }
    }

    public void setWebtoon(Webtoon webtoon) {
        WebtoonEntity webtoonEntity = WebtoonEntity.from(webtoon);
        this.webtoon = webtoonEntity;
        if (!webtoonEntity.getWebtoonGenres().contains(this)) {
            webtoonEntity.getWebtoonGenres().add(this);
        }
    }

    // 현재 단방향임
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
