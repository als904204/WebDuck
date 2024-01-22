package com.example.webduck.member.customMock;


import static org.assertj.core.api.Assertions.*;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomMockUserTest {

    @DisplayName("커스텀 MockMember 동작 확인")
    @Test
    @WithMockCustomUser(username="testUser")
    public void testCreateReview() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SessionMember sessionMember = (SessionMember) authentication.getPrincipal();

        assertThat(sessionMember.getUsername()).isEqualTo("testUser");

    }

}
