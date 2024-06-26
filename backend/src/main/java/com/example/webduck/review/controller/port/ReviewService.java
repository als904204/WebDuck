package com.example.webduck.review.controller.port;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.review.controller.response.ReviewLikesResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewCreate;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewService {

    Review findById(Long id);
    Review create(SessionMember sessionMember, ReviewCreate reviewCreate);

    void deleteReview(SessionMember sessionMember,Long reviewId);

    Double getAvg(Long webtoonId);

    int getCount(Long webtoonId);

    SliceResponse<ReviewSliceResponse> findReviewsByWebtoonId(Long webtoonId,
        Long nextReviewId, int page, int size, SessionMember member);

    @Transactional
    Review updateLikes(Long reviewId, SessionMember sessionMember);
}
