package com.example.webduck.review.infrastructure;

import static com.example.webduck.review.infrastructure.QReviewEntity.reviewEntity;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.webtoon.infrastructure.QWebtoonEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * @param webtoonId 조회할 webtoon
     * @param nextIdReq 불러올 review
     * @param pageable  페이징 조건
     * @return          no offset 페이징 리뷰 목록
     */
    @Override
    public SliceResponse<ReviewSliceResponse> findSliceReviews(Long webtoonId,Long nextIdReq,
        Pageable pageable) {

        int pageSize = pageable.getPageSize();

        QWebtoonEntity webtoon = QWebtoonEntity.webtoonEntity;


        List<ReviewSliceResponse> content = queryFactory.select(
                Projections.constructor(ReviewSliceResponse.class,
                    reviewEntity.id,
                    reviewEntity.content,
                    reviewEntity.reviewerNickname,
                    reviewEntity.memberId,
                    reviewEntity.rating,
                    reviewEntity.createdAt,
                    reviewEntity.likesCount
                ))
            .from(reviewEntity)
            .innerJoin(webtoon).on(reviewEntity.webtoonId.eq(webtoon.id))
            .where(webtoon.id.eq(webtoonId).and(ltReviewId(nextIdReq)))
            .limit(pageSize + 1)
            .orderBy(reviewEntity.id.desc())
            .fetch();

        /**
         * True
         *      item 이 15개고 size 요청이 10이면 limit(pageSize + 1) 11개의 item 조회
         *      item.size() = 11 > size(10)이 성립되므로
         *      10개의 데이터만 전송하고 나머지1개 즉 11번째 데이터 버림
         *
         * False
         *      item 이 5개고 size 요청이 10이면 limit(pageSize + 1) 11개의 item 을 조회하려 하지만
         *      실제론 5개의 item이 존재함
         *      item.size() = 5 < size(10) 성립이 안되므로 hasNext = false
         */
        boolean hasNext = false;
        Long nextIdRes = null;

        if (content.size() > pageSize) {
            content.remove(pageSize);
            ReviewSliceResponse lastReview = content.get(content.size() - 1);
            nextIdRes = lastReview.getReviewId();
            hasNext = true;
        }
        return new SliceResponse<>(content, pageable, hasNext,nextIdRes);
    }

    // 첫 조회라면 null 리턴 (id 를 기준으로 최신순으로 정렬 후 클라이언트에게 리턴)
    // reviewId != null, 요청 reviewId 보다 작은 review 목록 출력 즉 옛날 리뷰
    private BooleanExpression ltReviewId(Long nextIdReq) {
        // lt() : 작다
        // review.id < reviewId
        return isEmpty(nextIdReq) ? null : reviewEntity.id.lt(nextIdReq);
    }
}
