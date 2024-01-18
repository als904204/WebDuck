package com.example.webduck.webtoon.repository;

import com.example.webduck.genre.entity.QGenre;
import com.example.webduck.genre.entity.QWebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.entity.QReview;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonSortCondition.WebtoonConditionResponse;
import com.example.webduck.webtoon.entity.QWebtoon;
import com.querydsl.core.types.OrderSpecifier;
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

    @Override
    public List<WebtoonConditionResponse> findPopularWebtoonsByCondition(
        String condition) {
        QWebtoon webtoon = QWebtoon.webtoon;
        QReview review = QReview.review;

        return queryFactory.select(
                Projections.constructor(WebtoonConditionResponse.class,
                    webtoon.id,
                    webtoon.title,
                    webtoon.imagePath,
                    webtoon.originalImageName,
                    webtoon.summary,
                    webtoon.webtoonUrl,
                    webtoon.reviewCount,
                    review.rating.avg().as("rating_avg")
                ))
            .from(webtoon)
            .innerJoin(review).on(webtoon.id.eq(review.webtoonId))
            .groupBy(webtoon.id)
            .orderBy(sortByRatingOrReviewCount(condition))
            .limit(12)
            .fetch();
    }

    private OrderSpecifier<?> sortByRatingOrReviewCount(String condition) {
        QReview review = QReview.review;
        QWebtoon webtoon = QWebtoon.webtoon;
        return switch (condition.toUpperCase()) {
            case "RATING" -> review.rating.avg().desc();
            case "COUNT" -> webtoon.reviewCount.desc();
            default -> throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        };
    }
}
