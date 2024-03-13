package com.example.webduck.member.infrastructure;

import com.example.webduck.member.service.port.NicknameGenerator;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNicknameGenerator implements NicknameGenerator {
    private static final int MAX_SIZE = 10000;
    private static final String[] NAME_LIST = {
        "박독자",
        "최독자",
        "감상러",
        "웃긴툰",
        "툰파티",
        "툰방랑자",
        "책먹고툰",
        "미래작가",
        "마술사",
        "창의력툰",
        "감동맨",
        "웹툰코믹",
        "엡툰예술가",
        "웹덕귀신",
        "웹툰예언자",
        "캐릭터디자이너",
        "드라마티스트",
        "비주얼매니아",
        "팬티아티스트",
        "작품감상가"
    };

    @Override
    public String create() {
        Random random = new Random();
        String word = NAME_LIST[random.nextInt(NAME_LIST.length)];
        int randomNum = random.nextInt(MAX_SIZE);
        return word + "#" + randomNum;
    }
}
