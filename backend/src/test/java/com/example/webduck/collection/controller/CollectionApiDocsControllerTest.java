package com.example.webduck.collection.controller;


import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.collection.controller.port.CollectionService;
import com.example.webduck.collection.controller.response.CollectionDetailResponse;
import com.example.webduck.collection.controller.response.CollectionDetailResponse.WebtoonInfo;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionUpdate;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@WebMvcTest(CollectionApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ActiveProfiles("test")
class CollectionApiDocsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CollectionService collectionService;

    private final String uri = "/api/v1/collection";


    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();
    }

    @DisplayName("모든 보관함 조회")
    @Test
    void findAll() throws Exception {

        List<Collection> collections = List.of(Collection.builder()
            .id(1L)
            .title("힘숨찐 웹툰 추천")
            .description("주인공이.....")
            .ownerName("WebDuck")
            .isOwner(false)
            .build(),
            Collection.builder()
                .id(2L)
                .title("월요일 웹툰 추천")
                .description("월요일 웹툰")
                .ownerName("WebDuck")
                .isOwner(false)
                .build());

        when(collectionService.findAll(any())).thenReturn(collections);

        mockMvc.perform(get(uri))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-collections",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].id").description("보관함 ID"),
                    fieldWithPath("[].title").description("보관함 제목"),
                    fieldWithPath("[].description").description("보관함 설명"),
                    fieldWithPath("[].ownerName").description("보관함 작성자 닉네임"),
                    fieldWithPath("[].owner").description("보관함 작성자 여부")
                )
            ));
    }

    @DisplayName("ID로 보관함 단건 조회")
    @Test
    void findById() throws Exception {
        Long id = 1L;
        Collection collection = Collection.builder()
            .id(1L)
            .title("힘숨찐 웹툰 추천")
            .description("주인공이.....")
            .ownerName("WebDuck")
            .isOwner(false)
            .build();

        when(collectionService.findById(any())).thenReturn(collection);

        mockMvc.perform(get(uri + "/{id}", id))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-collection",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("id").description("보관함 ID"),
                    fieldWithPath("title").description("보관함 제목"),
                    fieldWithPath("description").description("보관함 설명"),
                    fieldWithPath("ownerName").description("보관함 작성자 닉네임"),
                    fieldWithPath("owner").description("보관함 작성자 여부")
                )
            ));
    }

    @DisplayName("보관함 생성")
    @Test
    void create() throws Exception {

        CollectionCreate collectionCreate = new CollectionCreate(
            "새 보관함 제목",
            "새 보관함 설명",
            List.of(1L, 2L, 3L)
        );

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collectionCreate)))
            .andExpect(status().isNoContent())
            .andDo(document("post-v1-create-collection",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").description("보관함 제목"),
                    fieldWithPath("description").description("보관함 설명"),
                    fieldWithPath("webtoonIds").description("보관함에 포함된 웹툰의 ID 목록")
                )
            ));
    }


    @DisplayName("보관함 수정")
    @Test
    void updateCollection() throws Exception {
        Long collectionId = 1L;

        CollectionUpdate collectionUpdate = new CollectionUpdate("업데이트된 제목", "업데이트된 설명", List.of(1L, 2L));

        Collection collection = Collection.builder()
            .id(1L)
            .title("업데이트된 제목")
            .description("업데이트된 설명")
            .ownerName("WebDuck")
            .isOwner(false)
            .build();

        when(collectionService.update(any(), any(), any())).thenReturn(collection);


        mockMvc.perform(patch(uri+"/{id}", collectionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collectionUpdate)))
            .andExpect(status().isNoContent())
            .andDo(document("patch-v1-update-collection",
                pathParameters(
                    parameterWithName("id").description("보관함 ID")
                ),
                requestFields(
                    fieldWithPath("title").description("보관함 제목"),
                    fieldWithPath("description").description("보관함 설명"),
                    fieldWithPath("webtoonIds").description("보관함에 포함된 웹툰 ID 목록")
                )
            ));
    }


    @DisplayName("보관함 삭제")
    @Test
    void deleteCollectionById() throws Exception {
        Long collectionId = 1L;

        doNothing().when(collectionService).deleteById(any(), any());
        mockMvc.perform(delete(uri+"/{id}", collectionId))
            .andExpect(status().isNoContent())
            .andDo(document("collection-delete",
                pathParameters(
                    parameterWithName("id").description("보관함 ID")
                )
            ));
    }

    @DisplayName("내 보관함 단건 조회")
    @Test
    void findMyCollection() throws Exception {

        Long id = 1L;

        Collection collection = Collection.builder()
            .id(1L)
            .title("힘숨찐 웹툰 추천")
            .description("주인공이 약한척함")
            .ownerName("WebDuck")
            .isOwner(false)
            .build();

        List<WebtoonInfo> webtoonInfos = List.of(
            new WebtoonInfo(1L, "나혼자만 레벨업"),
            new WebtoonInfo(2L, "전지적 독자시점")
        );
        CollectionDetailResponse collectionDetailResponse = CollectionDetailResponse.from(collection, webtoonInfos);
        when(collectionService.findMyCollection(any(), any())).thenReturn(collectionDetailResponse);


        mockMvc.perform(get(uri + "/{id}/member", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-myCollection",
                pathParameters(
                    parameterWithName("id").description("보관함 ID")
                ),
                responseFields(
                    fieldWithPath("id").description("보관함 ID"),
                    fieldWithPath("title").description("보관함 제목"),
                    fieldWithPath("description").description("보관함 설명"),
                    subsectionWithPath("webtoonInfo").description("보관함에 포함된 웹툰 정보"),
                    fieldWithPath("webtoonInfo[].id").description("웹툰 ID"),
                    fieldWithPath("webtoonInfo[].title").description("웹툰 제목")
                )
            ));

    }


}