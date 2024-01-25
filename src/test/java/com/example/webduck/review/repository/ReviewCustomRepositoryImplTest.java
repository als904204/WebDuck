package com.example.webduck.review.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.review.dto.SliceReviewResponse;
import com.example.webduck.review.entity.QReview;
import com.example.webduck.review.entity.Review;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.QWebtoon;
import com.example.webduck.webtoon.entity.Webtoon;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.transaction.annotation.Transactional;

@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReviewCustomRepositoryImplTest {


    @Autowired
    private TestEntityManager testEntityManager;
    private EntityManager em;
    private final Long webtoonId = 1L;
    private Webtoon webtoon;
    private Review review;
    private JPAQuery<Review> query;
    private QReview qReview;
    private QWebtoon qWebtoon;
    private Pageable pageable;
    int pageSize;

    @BeforeEach
    void setUp() {
        em = testEntityManager.getEntityManager();

        // 웹툰 저장
        webtoon = Webtoon
            .builder()
            .title("w1")
            .author("a1")
            .summary("s1")
            .platform(Platform.KAKAO)
            .imagePath("img")
            .publishDay(PublishDay.SUNDAY)
            .originalImageName("name")
            .build();
        testEntityManager.persist(webtoon);

        // 리뷰 30개 저장
        for (int i = 1; i <= 30; i++) {
            review = Review.builder()
                .webtoonId(webtoonId)
                .memberId(1L)
                .reviewerNickname("User1")
                .content("review content" + i)
                .rating(5)
                .build();
            testEntityManager.persist(review);
        }

        // pageable 요청
        pageable = PageRequest.of(0, 5);
        // QClass 설정
        query = new JPAQuery<>(em);
        qReview = new QReview("r");
        qWebtoon = new QWebtoon("w");
        pageSize = pageable.getPageSize();
    }

//    @DisplayName("리뷰 목록 30개중(size=5) 첫 요청 시 다음 ID 반환 및 hasNext true 여부")
//    @Test
//    void firstRequest() {
//        Long nextIdReq = null;
//
//        List<SliceReviewResponse> content = findSliceReviews(nextIdReq, pageSize);
//
//        boolean hasNext = false;
//        Long nextIdRes = null;
//
//        if (content.size() > pageSize) {
//            content.remove(pageSize);
//            SliceReviewResponse lastReview = content.get(content.size() - 1);
//            nextIdRes = lastReview.getReviewId();
//            hasNext = true;
//        }
//
//        assertThat(nextIdRes).isNotNull();
//        assertThat(nextIdRes).isEqualTo(26L);
//        assertThat(hasNext).isTrue();
//    }
//
//    @DisplayName("중간 페이지 요청 테스트")
//    @Test
//    void middleRequest() {
//        Long nextIdReq = 15L;
//
//        List<SliceReviewResponse> content = findSliceReviews(nextIdReq, pageSize);
//
//        boolean hasNext = false;
//        Long nextIdRes = null;
//
//        if (content.size() > pageSize) {
//            content.remove(pageSize);
//            SliceReviewResponse lastReview = content.get(content.size() - 1);
//            nextIdRes = lastReview.getReviewId();
//            hasNext = true;
//        }
//
//        assertThat(hasNext).isTrue();
//        assertThat(nextIdRes).isLessThan(15L);
//
//        assertThat(content.size()).isEqualTo(5);
//        assertThat(content.get(0).getReviewId()).isLessThan(15L);
//    }



    @DisplayName("리뷰 목록 30개중(size=5) 마지막 요청 시 다음 ID null 반환 및 hasNext false 여부")
    @Test
    void lastRequest() {
        Long nextIdReq = 6L;

        List<SliceReviewResponse> content = findSliceReviews(nextIdReq, pageSize);

        boolean hasNext = false;
        Long nextIdRes = null;

        if (content.size() > pageSize) {
            content.remove(pageSize);
            SliceReviewResponse lastReview = content.get(content.size() - 1);
            nextIdRes = lastReview.getReviewId();
            hasNext = true;
        }

        assertThat(hasNext).isFalse();
        assertThat(nextIdRes).isNull();
    }


    // ReviewCustomRepositoryImpl.findSliceReviews()
    private List<SliceReviewResponse> findSliceReviews(Long nextIdReq, int pageSize) {
        return query.select(
                Projections.constructor(SliceReviewResponse.class,
                    qReview.id,
                    qReview.content,
                    qReview.reviewerNickname,
                    qReview.memberId,
                    qReview.rating,
                    qReview.createdAt
                ))
            .from(qReview)
            .innerJoin(qWebtoon).on(qReview.webtoonId.eq(qWebtoon.id))
            .where(qWebtoon.id.eq(webtoonId).and(ltReviewId(nextIdReq)))
            .limit(pageSize + 1)
            .orderBy(qReview.id.desc())
            .fetch();
    }

    private BooleanExpression ltReviewId(Long nextIdReq) {
        QReview qReview = new QReview("r");
        return isEmpty(nextIdReq) ? null : qReview.id.lt(nextIdReq);
    }
}