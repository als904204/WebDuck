package com.example.webduck.review.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.dto.ReviewResponse.ReviewLikesResponse;
import com.example.webduck.review.dto.SliceReviewResponse;
import com.example.webduck.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ReviewId> createReview(@LoginMember SessionMember member,@Valid @RequestBody
    ReviewRequest reviewRequest) {
        ReviewId response = reviewService.saveReview(member, reviewRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{webtoonId}")
    public ResponseEntity<SliceResponse<SliceReviewResponse>> getReviewsByWebtoonId(
        @PathVariable Long webtoonId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size,
        @RequestParam(value = "nextId", required = false) Long nextId) {

        SliceResponse<SliceReviewResponse> reviews = reviewService.findReviewsByWebtoonId(webtoonId,
            nextId, page, size);

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("{webtoonId}/avg")
    public ResponseEntity<ReviewAvg> getReviewAvgByWebtoonId(@PathVariable Long webtoonId) {
        ReviewAvg response = reviewService.getReviewAvg(webtoonId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{webtoonId}/count")
    public ResponseEntity<ReviewCount> getReviewCountByWebtoonId(@PathVariable Long webtoonId) {
        ReviewCount response = reviewService.getReviewCount(webtoonId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{reviewId}/likes")
    public ResponseEntity<ReviewLikesResponse> updateReviewLikes(@PathVariable Long reviewId,
        @LoginMember SessionMember sessionMember) {
        ReviewLikesResponse reviewLikesResponse = reviewService.updateLikes(reviewId,
            sessionMember);
        return ResponseEntity.ok(reviewLikesResponse);
    }

}
