package com.example.webduck.review.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<ReviewResponse>> getReviewsByWebtoonId(@PathVariable Long webtoonId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByWebtoonId(webtoonId);
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

}
