package com.example.webduck.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webduck.member.customMock.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String uri = "/api/v1/member";

    @DisplayName("실패 : 로그인하지 않은 회원 프로필 조회 3xx 리다이렉트")
    @Test
    void testFailNoLogin() throws Exception {
        mockMvc.perform(get(uri + "/profile"))
            .andDo(print())
            .andExpect(status().is3xxRedirection());
    }

    @WithMockCustomUser
    @DisplayName("실패 : 회원 프로필 업데이트 필드값 누락 400 오류")
    @Test
    void testFailInvalidRequest() throws Exception{

        var request = """
                {
                    "username":""
                }
            """;

        mockMvc.perform(patch(uri + "/profile").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

}