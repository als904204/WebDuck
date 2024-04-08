package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.collection.controller.response.CollectionDetailResponse.WebtoonInfo;
import com.example.webduck.collection.infrastructure.QCollectionEntity;
import com.example.webduck.collection.infrastructure.QCollectionWebtoonsEntity;
import com.example.webduck.genre.entity.QGenre;
import com.example.webduck.genre.entity.QWebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.infrastructure.QReviewEntity;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity.WebtoonSortCondition;
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

    // SELECT w.*
    // FROM webtoon w
    // JOIN collection_webtoons cw ON w.id = cw.webtoon_id
    // WHERE cw.collection_id = 1;
    @Override
    public List<WebtoonEntity> findWebtoonsByCollectionId(Long collectionId) {


        QWebtoonEntity webtoon = QWebtoonEntity.webtoonEntity;
        QCollectionWebtoonsEntity collectionWebtoons = QCollectionWebtoonsEntity.collectionWebtoonsEntity;

        return queryFactory.select(webtoon)
            .from(webtoon)
            .join(collectionWebtoons).on(webtoon.id.eq(collectionWebtoons.webtoonId))
            .where(collectionWebtoons.collectionId.eq(collectionId))
            .fetch();
    }

    @Override
    public long deleteDuplicateWebtoons() {
        QWebtoonEntity webtoon1 = QWebtoonEntity.webtoonEntity;
        QWebtoonEntity webtoon2 = new QWebtoonEntity("webtoon2");

        QWebtoonGenre webtoonGenre = QWebtoonGenre.webtoonGenre;

        // 중복 ID 검색
        List<Long> duplicateIds = queryFactory
            .select(webtoon1.id)
            .from(webtoon1)
            .join(webtoon2).on(webtoon1.title.eq(webtoon2.title).and(webtoon1.id.gt(webtoon2.id)))
            .fetch();

        // 관련 장르 삭제
        queryFactory
            .delete(webtoonGenre)
            .where(webtoonGenre.webtoon.id.in(duplicateIds))
            .execute();


        // 웹툰 삭제
        return queryFactory
            .delete(webtoon1)
            .where(webtoon1.id.in(duplicateIds))
            .execute();
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
