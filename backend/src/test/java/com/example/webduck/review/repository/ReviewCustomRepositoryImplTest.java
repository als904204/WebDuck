package com.example.webduck.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.infrastructure.QReviewEntity;
import com.example.webduck.review.infrastructure.ReviewEntity;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;

import com.example.webduck.webtoon.infrastructure.QWebtoonEntity;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReviewCustomRepositoryImplTest {


    @Autowired
    private TestEntityManager testEntityManager;
    private EntityManager em;
    private final Long webtoonId = 1L;
    private WebtoonEntity webtoonEntity;
    private ReviewEntity reviewEntity;
    private JPAQuery<ReviewEntity> query;
    private QReviewEntity qReview;
    private QWebtoonEntity qWebtoon;
    private Pageable pageable;
    int pageSize;

    @BeforeEach
    void setUp() {
        em = testEntityManager.getEntityManager();

        // 웹툰 저장
        webtoonEntity = WebtoonEntity
            .builder()
            .title("w1")
            .author("a1")
            .summary("s1")
            .platform(Platform.KAKAO)
            .imagePath("img")
            .publishDay(PublishDay.SUNDAY)
            .originalImageName("name")
            .build();
        testEntityManager.persist(webtoonEntity);

        // 리뷰 30개 저장
        for (int i = 1; i <= 30; i++) {
            reviewEntity = ReviewEntity.builder()
                .webtoonId(webtoonId)
                .memberId(1L)
                .reviewerNickname("User1")
                .content("review content" + i)
                .rating(5)
                .build();
            testEntityManager.persist(reviewEntity);
        }

        // pageable 요청
        pageable = PageRequest.of(0, 5);
        // QClass 설정
        query = new JPAQuery<>(em);
        qReview = new QReviewEntity("r");
        qWebtoon = new QWebtoonEntity("w");
        pageSize = pageable.getPageSize();
    }



    @DisplayName("리뷰 목록 30개중(size=5) 마지막 요청 시 다음 ID null 반환 및 hasNext false 여부")
    @Test
    void lastRequest() {
        Long nextIdReq = 6L;

        List<ReviewSliceResponse> content = findSliceReviews(nextIdReq, pageSize);

        boolean hasNext = false;
        Long nextIdRes = null;

        if (content.size() > pageSize) {
            content.remove(pageSize);
            ReviewSliceResponse lastReview = content.get(content.size() - 1);
            nextIdRes = lastReview.getReviewId();
            hasNext = true;
        }

        assertThat(hasNext).isFalse();
        assertThat(nextIdRes).isNull();
    }


    // ReviewCustomRepositoryImpl.findSliceReviews()
    private List<ReviewSliceResponse> findSliceReviews(Long nextIdReq, int pageSize) {
        return query.select(
                Projections.constructor(ReviewSliceResponse.class,
                    qReview.id,
                    qReview.content,
                    qReview.reviewerNickname,
                    qReview.memberId,
                    qReview.rating,
                    qReview.createdAt,
                    qReview.likesCount
                ))
            .from(qReview)
            .innerJoin(qWebtoon).on(qReview.webtoonId.eq(qWebtoon.id))
            .where(qWebtoon.id.eq(webtoonId).and(ltReviewId(nextIdReq)))
            .limit(pageSize + 1)
            .orderBy(qReview.id.desc())
            .fetch();
    }

    private BooleanExpression ltReviewId(Long nextIdReq) {
        QReviewEntity qReview = new QReviewEntity("r");
        return isEmpty(nextIdReq) ? null : qReview.id.lt(nextIdReq);
    }
}