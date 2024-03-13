package com.example.webduck.review.controller;

import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.controller.port.ReviewLikesService;
import com.example.webduck.review.controller.port.ReviewService;
import com.example.webduck.review.controller.response.ReviewAvgResponse;
import com.example.webduck.review.controller.response.ReviewCountResponse;
import com.example.webduck.review.controller.response.ReviewCreateResponse;
import com.example.webduck.review.controller.response.ReviewLikesResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewCreate;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@RestController
public class ReviewApiController {

    private final ReviewService reviewService;
    private final ReviewLikesService reviewLikesService;

    @PostMapping
    public ResponseEntity<ReviewCreateResponse> create(@LoginMember SessionMember member,@Valid @RequestBody
    ReviewCreate reviewCreate) {
        ReviewCreateResponse response = ReviewCreateResponse.from(
            reviewService.create(member, reviewCreate));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
        @LoginMember SessionMember member) {
        reviewService.deleteReview(member, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{webtoonId}")
    public ResponseEntity<SliceResponse<ReviewSliceResponse>> findReviewsByWebtoonId(
        @PathVariable Long webtoonId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size,
        @RequestParam(value = "nextId", required = false) Long nextId,
        @LoginMember SessionMember member) {
        SliceResponse<ReviewSliceResponse> reviews = reviewService.findReviewsByWebtoonId(webtoonId,
            nextId, page, size,member);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("{webtoonId}/avg")
    public ResponseEntity<ReviewAvgResponse> getReviewAvgByWebtoonId(@PathVariable Long webtoonId) {
        ReviewAvgResponse response = ReviewAvgResponse.from(reviewService.getAvg(webtoonId));

        return ResponseEntity.ok(response);
    }

    @GetMapping("{webtoonId}/count")
    public ResponseEntity<ReviewCountResponse> getReviewCountByWebtoonId(
        @PathVariable Long webtoonId) {
        ReviewCountResponse response = ReviewCountResponse.from(reviewService.getCount(webtoonId));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{reviewId}/likes")
    public ResponseEntity<ReviewLikesResponse> updateReviewLikes(@PathVariable Long reviewId,
        @LoginMember SessionMember sessionMember) {

        ReviewLikesResponse response = ReviewLikesResponse.from(
            reviewLikesService.updateLikes(reviewId, sessionMember));

        return ResponseEntity.ok(response);
    }

}
