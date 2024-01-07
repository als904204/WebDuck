package com.example.webduck.webtoon.repository;

import com.example.webduck.genre.entity.QGenre;
import com.example.webduck.genre.entity.QWebtoonGenre;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.entity.QWebtoon;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebtoonGenreCustomImpl implements WebtoonGenreCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames) {
        QWebtoon webtoon = QWebtoon.webtoon;
        QWebtoonGenre webtoonGenre = QWebtoonGenre.webtoonGenre;
        QGenre genre = QGenre.genre;

        return queryFactory.select(
                Projections.constructor(WebtoonGenreResponse.class,
                    webtoon.id,
                    webtoon.title,
                    webtoon.imagePath,
                    webtoon.originalImageName
                ))
            .from(webtoon)
            .innerJoin(webtoonGenre).on(webtoonGenre.webtoon.id.eq(webtoon.id))
            .innerJoin(genre).on(genre.id.eq(webtoonGenre.genre.id))
            .where(genre.name.in(genreNames))
            .groupBy(webtoon.id)
            .having(genre.name.countDistinct().eq((long) genreNames.size()))
            .fetch();
    }
}
