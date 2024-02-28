package com.example.webduck.member.dto;

import com.example.webduck.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {

    @Getter
    public static class UpdateNickname {
        private final String username;
        public UpdateNickname(Member member) {
            this.username = member.getUsername();
        }
    }


    @Getter
    public static class ProfileRequest{

        @NotBlank(message = "닉네임은 빈칸이 될 수 없습니다")
        @Size(min = 2,max = 16, message = "닉네임은 최대 16까지 가능합니다")
        private String username;
        public ProfileRequest(String username) {
            this.username = username;
        }
        public ProfileRequest() {}
    }

}
