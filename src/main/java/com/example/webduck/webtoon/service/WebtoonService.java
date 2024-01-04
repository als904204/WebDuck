package com.example.webduck.webtoon.service;

import com.example.webduck.webtoon.dto.WebtoonRequest;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;

    // ID로 웹툰 조회
    @Transactional(readOnly = true)
    public WebtoonRequest findWebtoonById(Long id) {
        Webtoon webtoon = webtoonRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                "can't find the webtoon"));
        return new WebtoonRequest(webtoon);
    }

    // 모든 웹툰 조회 TODO : 페이징 적용 해야 함 나중에
    @Transactional(readOnly = true)
    public List<WebtoonRequest> findWebtoonList() {
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream()
            .map(WebtoonRequest::new)
            .toList();
    }

    // 요청에 따른 요일 웹툰 목록 조회 (MONDAY,SUNDAY..)
    @Transactional(readOnly = true)
    public List<WebtoonRequest> findWebtoonsByPublishDay(PublishDay publishDay) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonByPublishDay(publishDay);
        return webtoons.stream()
            .map(WebtoonRequest::new)
            .toList();
    }

    // 요청에 따른 플랫폼 별 웹툰 목록 조회 (NAVER,KAKAO..)
    @Transactional(readOnly = true)
    public List<WebtoonRequest> findWebtoonsByPlatform(Platform platform) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonByPlatform(platform);
        return webtoons.stream()
            .map(WebtoonRequest::new)
            .toList();
    }

    // 요청에 따른 장르별 웹툰 목록 조회 (무협,로맨스..)
    @Transactional(readOnly = true)
    public List<WebtoonRequest> findWebtoonsByGenreName(String name) {
        System.out.println("WebtoonService.findWebtoonsByGenreName");
        name.toLowerCase();
        System.out.println("name = " + name);
        List<Webtoon> webtoonsByGenre = webtoonRepository.findByWebtoonsGenreName(name);
        return webtoonsByGenre.stream()
            .map(WebtoonRequest::new)
            .toList();
    }
}
