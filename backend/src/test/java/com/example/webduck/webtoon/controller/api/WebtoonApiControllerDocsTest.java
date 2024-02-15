package com.example.webduck.webtoon.controller.api;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.review.entity.Review;
import com.example.webduck.webtoon.dto.WebtoonDetails;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonResponse;
import com.example.webduck.webtoon.dto.WebtoonSortCondition.WebtoonConditionResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.service.WebtoonService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(WebtoonApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class WebtoonApiControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebtoonService webtoonService;

    private final String uri = "/api/v1/webtoon";

    private Webtoon webtoon1, webtoon2, webtoon3;

    private List<WebtoonResponse> mockWebtoonResponses;

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();

        webtoon1 = Webtoon.builder()
            .title("w1 title")
            .summary("w1 summary")
            .originalImageName("w1 imgName")
            .imagePath("w1 imgPath")
            .publishDay(PublishDay.THURSDAY)
            .platform(Platform.NAVER)
            .author("w1 author")
            .build();

        webtoon2 = Webtoon.builder()
            .title("w2 title")
            .summary("w2 summary")
            .originalImageName("w2 imgName")
            .imagePath("w2 imgPath")
            .publishDay(PublishDay.THURSDAY)
            .platform(Platform.NAVER)
            .author("w2 author")
            .build();

        webtoon3 = Webtoon.builder()
            .title("w3 title")
            .summary("w3 summary")
            .originalImageName("w3 imgName")
            .imagePath("w3 imgPath")
            .publishDay(PublishDay.THURSDAY)
            .platform(Platform.NAVER)
            .author("w3 author")
            .build();

        mockWebtoonResponses = List.of(
            new WebtoonResponse(webtoon1),
            new WebtoonResponse(webtoon2),
            new WebtoonResponse(webtoon3)
        );
    }


    @DisplayName("조회 : 모든 웹툰 조회")
    @Test
    void testWebtoonList() throws Exception {

        Mockito.when(webtoonService.findWebtoonList()).thenReturn(mockWebtoonResponses);

        mockMvc.perform(get(uri))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoons",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].id").description("웹툰 ID"),
                    fieldWithPath("[].title").description("웹툰 제목"),
                    fieldWithPath("[].summary").description("웹툰 줄거리"),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명"),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로"),
                    fieldWithPath("[].publishDay").description("연재 요일"),
                    fieldWithPath("[].platform").description("웹툰 플랫폼"),
                    fieldWithPath("[].author").description("웹툰 작가")
                )
            ));
    }

    @DisplayName("조회 : 요일별 웹툰 조회")
    @Test
    void testWebtoonListByPublish() throws Exception {

        final String endpoint = "/publish";
        String thursday = PublishDay.THURSDAY.name();

        Mockito.when(webtoonService.findWebtoonsByPublishDay(Mockito.any(PublishDay.class)))
            .thenReturn(mockWebtoonResponses);

        mockMvc.perform(get(uri + endpoint)
                .param("day", thursday))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonsByPublish",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("day").description("요일별 웹툰을 조회하기 위한 파라미터. 가능한 값: " +
                        Arrays.stream(PublishDay.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", ")))
                ),
                responseFields(
                    fieldWithPath("[].id").description("웹툰의 ID").type(Long.class),
                    fieldWithPath("[].title").description("웹툰의 제목").type(JsonFieldType.STRING),
                    fieldWithPath("[].summary").description("웹툰의 요약").type(JsonFieldType.STRING),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("[].publishDay").description("웹툰의 연재 요일")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("[].platform").description("웹툰이 연재되는 플랫폼")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("[].author").description("웹툰의 작가").type(JsonFieldType.STRING)
                )
            ));

    }

    @DisplayName("조회 : 플랫폼별 웹툰 조회")
    @Test
    void testWebtoonListByPlatform() throws Exception {

        final String endpoint = "/platform";

        Mockito.when(webtoonService.findWebtoonsByPlatform(Mockito.any(Platform.class)))
            .thenReturn(mockWebtoonResponses);

        mockMvc.perform(get(uri + endpoint)
                .param("type", Platform.NAVER.name()))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonsByPlatform",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("type").description("플랫폼별 웹툰을 조회하기 위한 파라미터. 가능한 값: " +
                        Arrays.stream(Platform.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", ")))
                ),
                responseFields(
                    fieldWithPath("[].id").description("웹툰의 ID").type(Long.class),
                    fieldWithPath("[].title").description("웹툰의 제목").type(JsonFieldType.STRING),
                    fieldWithPath("[].summary").description("웹툰의 요약").type(JsonFieldType.STRING),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명").type(JsonFieldType.STRING),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로").type(JsonFieldType.STRING),
                    fieldWithPath("[].publishDay").description("웹툰의 연재 요일").type(JsonFieldType.STRING),
                    fieldWithPath("[].platform").description("웹툰이 연재되는 플랫폼").type(JsonFieldType.STRING),
                    fieldWithPath("[].author").description("웹툰의 작가").type(JsonFieldType.STRING)
                )
            ));
    }

    @DisplayName("조회 : 장르별 웹툰 조회")
    @Test
    void testWebtoonListByGenre() throws Exception {
        String[] genres = {"ROMANCE", "FANTASY", "GAG", "ACTION", "DAILY_LIFE", "THRILLER", "MARTIAL_ARTS", "DRAMA", "SPORTS", "ADULT"};

        Mockito.when(webtoonService.findWebtoonsByGenreName(Mockito.anyString()))
            .thenReturn(mockWebtoonResponses);

        mockMvc.perform(get(uri + "/genre")
                .param("name", "FANTASY"))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonsByGenre",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("name").description("웹툰을 조회하기 위한 장르 이름. 가능한 값: " + String.join(", ", genres))
                ),
                responseFields(
                    fieldWithPath("[].id").description("웹툰의 ID").type(Long.class),
                    fieldWithPath("[].title").description("웹툰의 제목").type(JsonFieldType.STRING),
                    fieldWithPath("[].summary").description("웹툰의 요약").type(JsonFieldType.STRING),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명").type(JsonFieldType.STRING),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로").type(JsonFieldType.STRING),
                    fieldWithPath("[].publishDay").description("웹툰의 연재 요일").type(JsonFieldType.STRING),
                    fieldWithPath("[].platform").description("웹툰이 연재되는 플랫폼").type(JsonFieldType.STRING),
                    fieldWithPath("[].author").description("웹툰의 작가").type(JsonFieldType.STRING)
                )
            ));
    }

    @DisplayName("조회: 장르별 웹툰 목록")
    @Test
    void testWebtoonListByGenreNames() throws Exception {
        String[] genres = {"ROMANCE", "FANTASY", "GAG", "ACTION", "DAILY_LIFE", "THRILLER", "MARTIAL_ARTS", "DRAMA", "SPORTS", "ADULT"};

        List<WebtoonGenreResponse> mockWebtoonGenreResponses = List.of(
            new WebtoonGenreResponse(1L, "로맨스&액션 웹툰1", "imgPath1",
                "imgName1"),
            new WebtoonGenreResponse(2L, "로맨스&액션 웹툰2", "imgPath1",
                "imgName2")
        );

        List<String> mockNames = Arrays.asList("ROMANCE", "ACTION");

        Mockito.when(webtoonService.findWebtoonsByGenreNames(Mockito.anyList()))
            .thenReturn(mockWebtoonGenreResponses);

        mockMvc.perform(get(uri +"/genres")
                .param("names", String.join(",", mockNames)))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonsByGenres",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("names").description("웹툰 장르로 필터링 (여러가지 선택 가능):"+ String.join(", ", genres))
                ),
                responseFields(
                    fieldWithPath("[].id").description("웹툰의 ID").type(Long.class),
                    fieldWithPath("[].title").description("웹툰의 제목").type(JsonFieldType.STRING),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명").type(JsonFieldType.STRING),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로").type(JsonFieldType.STRING)
                )
            ));
    }

    @DisplayName("조회: 조건별 인기 웹툰 목록")
    @Test
    void testPopularWebtoonListByCondition() throws Exception {

        List<WebtoonConditionResponse> mockWebtoonConditionResponses = List.of(
            new WebtoonConditionResponse(
                2L,
                "화산귀환",
                "/images/화산귀환.jpg",
                "화산귀환.jpg",
                "화산귀환 줄거리",
                "http://example.com/webtoon2",
                50,
                5.0
            ),
            new WebtoonConditionResponse(
                1L,
                "나혼자만 레벨업",
                "/images/나혼자만 레벨업.jpg",
                "나혼자만 레벨업.jpg",
                "나혼자만 레벨업 줄거리",
                "http://example.com/webtoon1",
                100,
                3.0
            )
        );

        String sortBy = "rating"; // 'rating' 또는 'count'

        Mockito.when(webtoonService.findWebtoonsByWebtoonCondition(Mockito.anyString()))
            .thenReturn(mockWebtoonConditionResponses);

        mockMvc.perform(get(uri+"/popular")
                .param("sortBy", sortBy))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonsByCondition",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("sortBy").description("정렬 기준 (평점순,리뷰순): 'rating', 'count'")
                ),
                responseFields(
                    fieldWithPath("[].id").description("웹툰의 ID").type(Long.class),
                    fieldWithPath("[].title").description("웹툰의 제목").type(JsonFieldType.STRING),
                    fieldWithPath("[].summary").description("웹툰의 요약").type(JsonFieldType.STRING),
                    fieldWithPath("[].originalImageName").description("원본 이미지 파일명").type(JsonFieldType.STRING),
                    fieldWithPath("[].imagePath").description("이미지 파일 경로").type(JsonFieldType.STRING),
                    fieldWithPath("[].webtoonUrl").description("해당 웹툰 바로가기 링크").type(JsonFieldType.STRING),
                    fieldWithPath("[].reviewCount").description("웹툰 리뷰 수").type(JsonFieldType.NUMBER),
                    fieldWithPath("[].ratingAvg").description("웹툰 리뷰 평균 점수").type(JsonFieldType.NUMBER)
                )
            ));
    }

    @DisplayName("조회 : 웹툰 상세보기")
    @Test
    void testGetWebtoonDetails() throws Exception {

        var webtoonId = 1L;
        Webtoon webtoon = Webtoon.builder()
            .title("웹툰 제목")
            .summary("웹툰 줄거리")
            .imagePath("썸네일 이미지 경로")
            .publishDay(PublishDay.MONDAY)
            .platform(Platform.NAVER)
            .author("웹툰 작가")
            .webtoonUrl("웹툰 바로가기 링크")
            .originalImageName("썸네일 이미지 파일 이름")
            .build();

        List<Review> reviews = List.of(
            Review.builder()
                .webtoonId(webtoonId)
                .reviewerNickname("user1")
                .memberId(1L)
                .content("리뷰내용1")
                .rating(5)
                .build(),
            Review.builder()
                .webtoonId(webtoonId)
                .reviewerNickname("user1")
                .memberId(1L)
                .content("리뷰내용2")
                .rating(5)
                .build()
        );



        int reviewCount = reviews.size();
        Double webtoonAvg = Review.calculateRatingAvg(reviews);

        WebtoonDetails response = new WebtoonDetails(webtoon,reviewCount,webtoonAvg);
        Mockito.when(webtoonService.getWebtoonDetails(Mockito.any(Long.class)))
            .thenReturn(response);

        mockMvc.perform(get(uri + "/{id}", webtoonId))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-webtoonDetails",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("조회할 웹툰 ID")
                ),
                responseFields(
                    fieldWithPath("webtoonId").description("웹툰 ID").type(Long.class),
                    fieldWithPath("title").description("웹툰 제목"),
                    fieldWithPath("summary").description("웹툰 줄거리"),
                    fieldWithPath("originalImageName").description("썸네일 이미지 파일 이름"),
                    fieldWithPath("imagePath").description("썸네일 이미지 파일 경로"),
                    fieldWithPath("publishDay").description("웹툰 출간 요일"),
                    fieldWithPath("platform").description("웹툰 플랫폼"),
                    fieldWithPath("author").description("웹툰 작가"),
                    fieldWithPath("webtoonUrl").description("웹툰 바로가기 링크"),
                    fieldWithPath("reviewCount").description("리뷰 수"),
                    fieldWithPath("webtoonRating").description("웹툰 리뷰 평균 평점")
                )
            ));
    }

}