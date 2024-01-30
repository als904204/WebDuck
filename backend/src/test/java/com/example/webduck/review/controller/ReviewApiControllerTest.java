package com.example.webduck.review.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.customMock.WithMockCustomUser;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.service.ReviewService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ReviewApiController.class)
class ReviewApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private final String uri = "/api/v1/review";


    @WithMockCustomUser
    @DisplayName("유효성 검사 실패 테스트 : 리뷰 content 빈 값")
    @Test
    void testCreateReview_fail_contentIsNull() throws Exception {
        var req = """
            {
                "webtoonId" : 1,
                "content" : "",
                "rating" :5
            }
            """;

        var webtoonId = 1L;
        List<ReviewResponse> mockResponses = List.of(new ReviewResponse(
            Review.builder()
                .reviewerNickname("user")
                .content("content")
                .rating(4)
                .webtoonId(1L)
                .memberId(1L)
                .build(
                )));

//        when(reviewService.getReviewsByWebtoonId(webtoonId)).thenReturn(mockResponses);

        mockMvc.perform(post(uri).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
            .andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("유효성 검사 실패 테스트 : rating 범위 벗어남")
    @Test
    void testCreateReview_fail_overRating() throws Exception {

        var req = """
            {
                "webtoonId" : 1,
                "content" : "content",
                "rating" : 20
            }
            """;
        var reviewId = 1L;

        ReviewId mockResponse = new ReviewId(reviewId);

        when(reviewService.saveReview(Mockito.any(SessionMember.class),Mockito.any(ReviewRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post(uri).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
            .andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("리뷰 유효성 검사 실패 테스트 : 잘못된 ID")
    @Test
    void testReviewsByWebtoonId_fail_invalidId() throws Exception {
        var webtoonId = "asfdafdzv1231";

        mockMvc.perform(get(uri + "/{webtoonId}", webtoonId)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
        ;
    }
}