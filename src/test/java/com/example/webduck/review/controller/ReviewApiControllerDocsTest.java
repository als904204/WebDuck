package com.example.webduck.review.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.customMock.MockMemberUtil;
import com.example.webduck.member.customMock.WithMockCustomUser;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(ReviewApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class ReviewApiControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private final String uri = "/api/v1/review";

    private SessionMember sessionMember;

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();
    }

    @DisplayName("생성 :웹툰 리뷰 생성")
    @Test
    @WithMockCustomUser
    public void testCreateReview() throws Exception {

        ReviewRequest request = new ReviewRequest(1L, "first review", 5);
        ReviewId responseReviewId = new ReviewId(1L);

        Mockito.when(reviewService.saveReview(Mockito.any(SessionMember.class), Mockito.any(
            ReviewRequest.class))).thenReturn(responseReviewId);

        sessionMember = MockMemberUtil.getMockSessionMember();


        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .sessionAttr("member", sessionMember))
            .andExpect(status().isOk())
            .andDo(document("post-v1-post-review",
                requestFields(
                    fieldWithPath("webtoonId").description("웹툰의 ID"),
                    fieldWithPath("content").description("리뷰 내용"),
                    fieldWithPath("rating").description("리뷰 평점")
                ),
                responseFields(
                    fieldWithPath("reviewId").description("생성된 리뷰 ID")
                )
            ));
    }

    @DisplayName("조회 : 웹툰 리뷰 조회")
    @Test
    void testFindReviewsByWebtoonId() throws Exception {
        Long webtoonId = 1L;
        Review review1 = Review.builder()
            .webtoonId(webtoonId)
            .reviewerNickname("webduck")
            .content("first review!!")
            .rating(5)
            .build();

        Review review2 = Review.builder()
            .webtoonId(webtoonId)
            .reviewerNickname("webduck")
            .content("second review!!")
            .rating(3)
            .build();

        List<ReviewResponse> reviewResponses = List.of(
            new ReviewResponse(review1),
            new ReviewResponse(review2)
        );

        Mockito.when(reviewService.getReviewsByWebtoonId(Mockito.any(Long.class)))
            .thenReturn(reviewResponses);

        mockMvc.perform(get(uri + "/{webtoonId}",webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-review",
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 조회할 웹툰 ID")
                ),
                responseFields(
                    fieldWithPath("[].authorId").description("리뷰 작성자 ID"),
                    fieldWithPath("[].reviewerNickname").description("리뷰 작성자 닉네임"),
                    fieldWithPath("[].content").description("리뷰 내용"),
                    fieldWithPath("[].rating").description("리뷰 평균 점수")
                )
            ));
    }

    @DisplayName("조회 : 리뷰 평균 점수")
    @Test
    void testGetReviewAvgByWebtoonId() throws Exception {
        Long webtoonId = 1L;
        ReviewAvg reviewAvgRes = new ReviewAvg(4.0);

        Mockito.when(reviewService.getReviewAvg(Mockito.any(Long.class)))
            .thenReturn(reviewAvgRes);

        mockMvc.perform(get(uri + "/{webtoonId}/avg", webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewRating",
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 평균점수 조회할 웹툰 ID")
                ),
                responseFields(
                    fieldWithPath("rating").description("리뷰 평균점수")
                )
            ));

    }

    @DisplayName("조회 : 리뷰 개수")
    @Test
    void testGetReviewCountByWebtoonId() throws Exception {
        Long webtoonId = 1L;
        ReviewCount reviewCount = new ReviewCount(250);

        Mockito.when(reviewService.getReviewCount(Mockito.any(Long.class)))
            .thenReturn(reviewCount);

        mockMvc.perform(get(uri + "/{webtoonId}/count", webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewCount",
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 개수 조회할 웹툰 ID")
                ),
                responseFields(
                    fieldWithPath("count").description("리뷰 개수")
                )
            ));

    }

}