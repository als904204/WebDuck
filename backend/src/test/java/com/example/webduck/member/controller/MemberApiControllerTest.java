package com.example.webduck.member.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.MemberUpdate;
import com.example.webduck.mock.member.MockMemberUtil;
import com.example.webduck.mock.member.WithMockCustomUser;
import com.example.webduck.member.service.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberServiceImpl;

    private final String uri = "/api/v1/member";

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockCustomUser
    @DisplayName("실패 : 회원 프로필 업데이트 필드값 누락 400 오류")
    @Test
    void testFailInvalidRequest() throws Exception{

        MemberUpdate request = new MemberUpdate("");
        SessionMember sessionMember = MockMemberUtil.getMockSessionMember();

        mockMvc.perform(patch(uri + "/profile").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            .sessionAttr("member", sessionMember))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

}