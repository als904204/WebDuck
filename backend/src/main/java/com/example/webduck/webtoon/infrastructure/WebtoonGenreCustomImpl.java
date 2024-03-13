package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.genre.entity.QGenre;
import com.example.webduck.genre.entity.QWebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.infrastructure.QReviewEntity;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
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
        QWebtoonEntity webtoon = QWebtoonEntity.webtoonEntity;

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
    public List<WebtoonPopularResponse> findPopularWebtoonsByCondition(
        WebtoonSortCondition condition) {
        QWebtoonEntity webtoon = QWebtoonEntity.webtoonEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;

        return queryFactory.select(
                Projections.constructor(WebtoonPopularResponse.class,
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

    private OrderSpecifier<?> sortByRatingOrReviewCount(WebtoonSortCondition condition) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QWebtoonEntity webtoon = QWebtoonEntity.webtoonEntity;

        String sortBy = condition.getCondition();

        OrderSpecifier<?> orderSpecifier;
        switch (sortBy.toUpperCase()) {
            case "RATING":
                orderSpecifier = review.rating.avg().desc();
                break;
            case "COUNT":
                orderSpecifier = webtoon.reviewCount.desc();
                break;
            default:
                throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
        return orderSpecifier;
    }

}
