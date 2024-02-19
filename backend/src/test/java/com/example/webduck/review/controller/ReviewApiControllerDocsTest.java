package com.example.webduck.review.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.member.customMock.MockMemberUtil;
import com.example.webduck.member.customMock.WithMockCustomUser;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.dto.ReviewResponse.ReviewLikesResponse;
import com.example.webduck.review.dto.SliceReviewResponse;
import com.example.webduck.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ActiveProfiles("test")
@WebMvcTest(ReviewApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class ReviewApiControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private final String uri = "/api/v1/review";

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();
    }

    @DisplayName("생성 : 웹툰 리뷰 생성")
    @Test
    @WithMockCustomUser
    public void testCreateReview() throws Exception {

        // given
        var request = new ReviewRequest(1L, "first review", 5);
        var responseReviewId = new ReviewId(1L);

        Mockito.when(reviewService.saveReview(Mockito.any(SessionMember.class), Mockito.any(
            ReviewRequest.class))).thenReturn(responseReviewId);

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .sessionAttr("member", sessionMember))
            .andExpect(status().isOk())
            .andDo(document("post-v1-post-review",
                preprocessResponse(prettyPrint()),
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

    @DisplayName("조회 : 리뷰 목록(no offset)")
    @Test
    void testGetReviewsByWebtoonID() throws Exception {

        var webtoonId = 1L;
        var nextId = 2L;
        var page = 0;
        var size = 5;

        LocalDateTime now = LocalDateTime.now();

        // given
        var reviewResponses = List.of(
            new SliceReviewResponse(1L, "summary1", "nickname1", 1L, 5, now,2),
            new SliceReviewResponse(2L, "summary2", "nickname2", 2L, 4, now,2),
            new SliceReviewResponse(3L, "summary2", "nickname2", 2L, 4, now,2)
        );

        var sliceResponse = new SliceResponse<>(
            reviewResponses, PageRequest.of(page, size), true, nextId
        );

        // when
        Mockito.when(
                reviewService.findReviewsByWebtoonId(Mockito.any(Long.class), Mockito.any(Long.class),
                    Mockito.any(Integer.class), Mockito.any(Integer.class
                    )))
            .thenReturn(sliceResponse);

        // then
        mockMvc.perform(get(uri + "/{webtoonId}", webtoonId)
                .param("page", "0")
                .param("size", "5")
                .param("nextId", "2"))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewsByWebtoonId",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 목록 조회할 웹툰 ID")
                ),
                queryParameters(
                    parameterWithName("page").description("요청할 페이지 번호"),
                    parameterWithName("size").description("요청할 페이지당 항목 수"),
                    parameterWithName("nextId").description("다음 페이지를 위한 마지막 리뷰 ID (첫 요청 시 Null로 요청)").optional()
                ),
                responseFields(
                    fieldWithPath("item[]").description("리뷰 목록"),
                    fieldWithPath("item[].reviewId").description("리뷰 ID"),
                    fieldWithPath("item[].content").description("리뷰 내용"),
                    fieldWithPath("item[].reviewerNickname").description("리뷰어 닉네임"),
                    fieldWithPath("item[].authorId").description("리뷰 작성자 ID"),
                    fieldWithPath("item[].rating").description("리뷰 평점"),
                    fieldWithPath("item[].likesCount").description("리뷰 좋아요"),
                    fieldWithPath("item[].createdAt").description("리뷰 작성 시간"),
                    fieldWithPath("pageNumber").description("현재 페이지 번호"),
                    fieldWithPath("pageSize").description("페이지당 항목 수"),
                    fieldWithPath("hasNext").description("다음 페이지 존재 여부"),
                    fieldWithPath("count").description("현재 페이지의 항목 수"),
                    fieldWithPath("nextId").description("다음 페이지를 위한 마지막 리뷰 ID").optional()
                )

            ));

    }



    @DisplayName("조회 : 리뷰 평균 점수")
    @Test
    void testGetReviewAvgByWebtoonId() throws Exception {
        var webtoonId = 1L;
        var reviewAvgRes = new ReviewAvg(4.0);

        Mockito.when(reviewService.getReviewAvg(Mockito.any(Long.class)))
            .thenReturn(reviewAvgRes);

        mockMvc.perform(get(uri + "/{webtoonId}/avg", webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewRating",
                preprocessResponse(prettyPrint()),
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
        var webtoonId = 1L;
        var reviewCount = new ReviewCount(250);

        Mockito.when(reviewService.getReviewCount(Mockito.any(Long.class)))
            .thenReturn(reviewCount);

        mockMvc.perform(get(uri + "/{webtoonId}/count", webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewCount",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 개수 조회할 웹툰 ID")
                ),
                responseFields(
                    fieldWithPath("count").description("리뷰 개수")
                )
            ));

    }

    @WithMockCustomUser
    @DisplayName("수정 : 리뷰 좋아요")
    @Test
    void testUpdateReviewLikes() throws Exception {
        var reviewId = 1L;

        var reviewLikesResponse = new ReviewLikesResponse(true, 20);



        Mockito.when(
                reviewService.updateLikes(Mockito.any(Long.class), Mockito.any(SessionMember.class)))
            .thenReturn(reviewLikesResponse);

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        mockMvc.perform(patch(uri + "/{reviewId}/likes", reviewId)
                .sessionAttr("member", sessionMember))
            .andExpect(status().isOk())
            .andDo(document("patch-v1-update-reviewLikes",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("reviewId").description("조회할 리뷰 ID")
                ),
                responseFields(
                    fieldWithPath("success").description("성공/실패 여부"),
                    fieldWithPath("likesCount").description("좋아요 개수")
                )
            ));



    }

}