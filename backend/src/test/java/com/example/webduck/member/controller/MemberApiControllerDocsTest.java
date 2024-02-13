package com.example.webduck.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.autoconfigure.AutoConfigurationPackages.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.customMock.MockMemberUtil;
import com.example.webduck.member.customMock.WithMockCustomUser;
import com.example.webduck.member.dto.MemberProfile.ProfileRequest;
import com.example.webduck.member.dto.MemberProfile.ProfileResponse;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@WebMvcTest(MemberApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class MemberApiControllerDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private final String uri = "/api/v1/member";

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))// rest docs 설정 주입
            .alwaysDo(print()) // andDo(print()) 코드 포함
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
            .build();
    }

    @WithMockCustomUser
    @DisplayName("조회 : 회원 프로필")
    @Test
    void getMemberProfile() throws Exception {

        var member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(1L);
        Mockito.when(member.getUsername()).thenReturn("WebDuck");

        var response = new ProfileResponse(member);

        Mockito.when(memberService.getMemberProfile(Mockito.any(SessionMember.class)))
            .thenReturn(response);

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        mockMvc.perform(get(uri + "/profile")
                .sessionAttr("member", sessionMember))
            .andExpect(status().isOk())
            .andDo(document("get-v1-get-profile",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("username").description("회원 닉네임")
                    )
                )
            );
    }

    @WithMockCustomUser
    @DisplayName("수정 : 회원 프로필")
    @Test
    void updateMemberProfile() throws Exception {
        var member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(1L);
        Mockito.when(member.getUsername()).thenReturn("WebDuck");

        var updateUsername = "newWebDuck";
        var updateReq = new ProfileRequest(updateUsername);
        var updateRes = new ProfileResponse(member);

        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        Mockito.when(memberService.updateMemberProfile(Mockito.any(SessionMember.class),
            Mockito.any(ProfileRequest.class))).thenReturn(updateRes);

        mockMvc.perform(patch(uri + "/profile")
            .sessionAttr("member", sessionMember)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updateReq)))
            .andExpect(status().isOk())
            .andDo(document(
                "patch-v1-update-profile",
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("username").description("변경할 회원 닉네임")
                ),
                responseFields(
                    fieldWithPath("username").description("변경된 회원 닉네임")
                )
            ));


    }
}