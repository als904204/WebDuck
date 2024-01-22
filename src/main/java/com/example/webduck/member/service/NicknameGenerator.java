package com.example.webduck.member.service;

import com.example.webduck.member.repository.MemberRepository;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NicknameGenerator {

    private static final int MAX_SIZE = 100000;

    private final MemberRepository memberRepository;

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

    private static final Random RANDOM = new Random();

    public String getRandomNickname() {

        String generatedName;
        boolean isExistsName;

        do {
            generatedName = generateRandomNickname();
            isExistsName = memberRepository.findByUsername(generatedName).isPresent();
        } while (isExistsName);

        return generatedName;
    }

    private String generateRandomNickname() {
        String word = NAME_LIST[RANDOM.nextInt(NAME_LIST.length)];
        int randomNum = RANDOM.nextInt(MAX_SIZE);

        StringBuilder randomString = new StringBuilder();
        return randomString.append(word).append("#").append(randomNum).toString();
    }
}
