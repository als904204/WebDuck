package com.example.webduck.member.service;

import com.example.webduck.member.repository.MemberRepository;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NicknameGeneratorTest {

    @Autowired
    MemberRepository memberRepository;
    private static final Random RANDOM = new Random();

    private static final String[] NAME_LIST = {
        "글로벌툰",
        "툰크리에이터",
        "감상러",
        "웃긴툰",
        "툰파티",
        "툰방랑자",
        "책먹고툰",
        "미래작가",
        "마술사",
        "창의력툰",
        "감동맨",
        "툰코믹",
        "툰예술가",
        "툰덕귀신",
        "툰마스터",
        "툰스토리텔러",
        "툰예언자",
        "캐릭터디자이너",
        "드라마티스트",
        "비주얼매니아",
        "팬티아티스트",
        "작품감상가"
    };

    @DisplayName("회원가입 중복 닉네임 : 1만명 가입중 100명이하여야 한다")
    @Test
    public void createMemberTest() {
        Set<String> generatedNames = new HashSet<>();
        int duplicateCount = 0;

        for (int i = 0; i < 10000; i++) {
            String generatedName = generateRandomNickname();
            if (!generatedNames.add(generatedName)) {
                duplicateCount++;
            }
        }
        Assertions.assertThat(duplicateCount).isLessThan(100);
    }

    private String generateRandomNickname() {
        String word = NAME_LIST[RANDOM.nextInt(NAME_LIST.length)];
        int randomNum = RANDOM.nextInt(100000);

        StringBuilder randomString = new StringBuilder();
        return randomString.append(word).append("#").append(randomNum).toString();
    }

}