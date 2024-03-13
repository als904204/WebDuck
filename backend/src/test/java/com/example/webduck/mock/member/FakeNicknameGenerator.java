package com.example.webduck.mock.member;

import com.example.webduck.member.service.port.NicknameGenerator;

public class FakeNicknameGenerator implements NicknameGenerator {

    @Override
    public String create() {
        return "webduck#9999";
    }
}
