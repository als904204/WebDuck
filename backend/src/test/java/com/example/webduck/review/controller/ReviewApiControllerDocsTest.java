package com.example.webduck.review.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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

import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.mock.member.MockMemberUtil;
import com.example.webduck.mock.member.WithMockCustomUser;
import com.example.webduck.review.controller.response.ReviewLikesResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewCreate;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.service.ReviewServiceImpl;
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
    private ReviewServiceImpl reviewServiceImpl;

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

        Review review = Review.builder()
            .id(1L)
            .reviewerNickname("WebDuck")
            .content("fun!")
            .memberId(1L)
            .rating(5)
            .likesCount(0)
            .build();

        ReviewCreate request = new ReviewCreate(review.getId(), review.getContent(),
            review.getRating());

        Mockito.when(reviewServiceImpl.create(Mockito.any(SessionMember.class), Mockito.any(
            ReviewCreate.class))).thenReturn(review);

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
    @WithMockCustomUser
    void testGetReviewsByWebtoonID() throws Exception {

        var webtoonId = 1L;
        var nextId = 2L;
        var page = 0;
        var size = 5;

        LocalDateTime now = LocalDateTime.now();

        // given
        var reviewResponses = List.of(
            new ReviewSliceResponse(1L, "summary1", "nickname1", 1L, 5, now,2),
            new ReviewSliceResponse(2L, "summary2", "nickname2", 2L, 4, now,2),
            new ReviewSliceResponse(3L, "summary2", "nickname2", 2L, 4, now,2)
        );

        var sliceResponse = new SliceResponse<>(
            reviewResponses, PageRequest.of(page, size), true, nextId
        );

        // when
        Mockito.when(
            reviewServiceImpl.findReviewsByWebtoonId(
                Mockito.any(Long.class),
                Mockito.any(Long.class),
                Mockito.any(Integer.class),
                Mockito.any(Integer.class),
                Mockito.any(SessionMember.class))
        ).thenReturn(sliceResponse);

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();


        // then
        mockMvc.perform(get(uri + "/{webtoonId}", webtoonId)
                .param("page", "0")
                .param("size", "5")
                .param("nextId", "2")
                .sessionAttr("member", sessionMember))

            .andExpect(status().isOk())
            .andDo(document("get-v1-get-reviewsByWebtoonId",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("webtoonId").description("리뷰 목록 조회할 웹툰 ID")
                ),
                requestParameters(
                    parameterWithName("page").description("요청할 페이지 번호"),
                    parameterWithName("size").description("요청할 페이지당 항목 수"),
                    parameterWithName("nextId").description(
                        "다음 페이지를 위한 마지막 리뷰 ID (첫 요청 시 Null로 요청)").optional()
                ),
                responseFields(
                    fieldWithPath("item[]").description("리뷰 목록"),
                    fieldWithPath("item[].reviewId").description("리뷰 ID"),
                    fieldWithPath("item[].content").description("리뷰 내용"),
                    fieldWithPath("item[].reviewerNickname").description("리뷰어 닉네임"),
                    fieldWithPath("item[].authorId").description("리뷰 작성자 ID"),
                    fieldWithPath("item[].rating").description("리뷰 평점"),
                    fieldWithPath("item[].likesCount").description("리뷰 좋아요"),
                    fieldWithPath("item[].author").description("리뷰 작성자 여부"),
                    fieldWithPath("item[].createdAt").description("리뷰 작성 시간"),
                    fieldWithPath("pageNumber").description("현재 페이지 번호"),
                    fieldWithPath("pageSize").description("페이지당 항목 수"),
                    fieldWithPath("hasNext").description("다음 페이지 존재 여부"),
                    fieldWithPath("count").description("현재 페이지의 항목 수"),
                    fieldWithPath("nextId").description("다음 페이지를 위한 마지막 리뷰 ID").optional()
                )

            ));

    }


    @DisplayName("삭제 : 리뷰 삭제")
    @Test
    @WithMockCustomUser
    void testDeleteReview() throws Exception{
        var reviewId = 1L;

        Mockito.doNothing().when(reviewServiceImpl)
            .deleteReview(Mockito.any(SessionMember.class), Mockito.any(Long.class));

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        mockMvc.perform(delete(uri + "/{reviewId}", reviewId)
            .sessionAttr("member", sessionMember))
            .andExpect(status().isNoContent())
            .andDo(document("delete-v1-delete-review",
                preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("reviewId").description("삭제할 리뷰 ID")
                    )
                ));

    }

    @DisplayName("조회 : 리뷰 평균 점수")
    @Test
    void testGetReviewAvgByWebtoonId() throws Exception {
        var webtoonId = 1L;

        Double response = 5.0;

        Mockito.when(reviewServiceImpl.getAvg(Mockito.any(Long.class)))
            .thenReturn(response);

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
        int response = 10;

        Mockito.when(reviewServiceImpl.getCount(Mockito.any(Long.class)))
            .thenReturn(response);

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

        Long reviewId = 1L;
        Review review = Review.builder()
            .id(reviewId)
            .likesCount(50).build();


        Mockito.when(
                reviewServiceImpl.updateLikes(Mockito.any(Long.class), Mockito.any(SessionMember.class)))
            .thenReturn(review);

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
                    fieldWithPath("likesCount").description("좋아요 개수")
                )
            ));



    }

}