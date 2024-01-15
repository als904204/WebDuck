package com.example.webduck.review.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse;
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
    public ResponseEntity<Long> createReview(@LoginMember SessionMember member,@Valid @RequestBody
    ReviewRequest reviewRequest) {
        Long reviewId = reviewService.saveReview(member, reviewRequest);
        return ResponseEntity.ok(reviewId);
    }

    @GetMapping("/{webtoonId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByWebtoonId(@PathVariable Long webtoonId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByWebtoonId(webtoonId);
        return ResponseEntity.ok(reviews);
    }
}
