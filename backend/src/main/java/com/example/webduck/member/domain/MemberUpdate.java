package com.example.webduck.member.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdate {

    @NotBlank(message = "닉네임은 빈칸이 될 수 없습니다")
    @Size(min = 2,max = 16, message = "닉네임은 최대 16까지 가능합니다")
    private String username;

    public MemberUpdate(String username) {
        this.username = username;
    }


}
