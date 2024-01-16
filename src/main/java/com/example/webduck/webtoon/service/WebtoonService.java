package com.example.webduck.webtoon.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import com.example.webduck.webtoon.dto.WebtoonDetails;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonResponse;
import com.example.webduck.webtoon.dto.WebtoonSortCondition.WebtoonConditionResponse;
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

    private final ReviewRepository reviewRepository;

    // ID로 웹툰 조회
    @Transactional(readOnly = true)
    public WebtoonResponse findWebtoonById(Long id) {
        Webtoon webtoon = webtoonRepository.findById(id)
            .orElseThrow(() -> new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND));
        return new WebtoonResponse(webtoon);
    }

    // 웹툰 상세정보
    // 웹툰정보와 해당 웹툰의 리뷰 목록을 리턴
    @Transactional(readOnly = true)
    public WebtoonDetails getWebtoonDetails(Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
            .orElseThrow(() -> new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND));

        List<Review> reviews = reviewRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(webtoonId);

        List<ReviewResponse> reviewDto = reviews.stream()
            .map(ReviewResponse::new)
            .toList();

        return new WebtoonDetails(webtoon,reviewDto);
    }

    // 모든 웹툰 조회
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonList() {
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream()
            .map(WebtoonResponse::new)
            .toList();
    }

    // 요청에 따른 요일 웹툰 목록 조회 (MONDAY,SUNDAY..)
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByPublishDay(PublishDay publishDay) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPublishDay(publishDay);
        return webtoons.stream()
            .map(WebtoonResponse::new)
            .toList();
    }

    // 요청에 따른 플랫폼 별 웹툰 목록 조회 (NAVER,KAKAO..)
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByPlatform(Platform platform) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPlatform(platform);
        return webtoons.stream()
            .map(WebtoonResponse::new)
            .toList();
    }

    // 단건 장르 웹툰 목록 조회 (무협 웹툰만 조회,로맨스 웹툰만 조회)
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByGenreName(String name) {
        List<Webtoon> webtoonsByGenre = webtoonRepository.findWebtoonsByGenreName(name);
        return webtoonsByGenre.stream()
            .map(WebtoonResponse::new)
            .toList();
    }

    // 장르 요청에 따른 웹툰 목록 조회 (무협,개그,판타지 장르를 포함한 웹툰)
    @Transactional(readOnly = true)
    public List<WebtoonGenreResponse> findWebtoonsByGenreNames(List<String> genreNames) {
        return webtoonRepository.findWebtoonsByGenres(genreNames);
    }

    @Transactional(readOnly = true)
    public List<WebtoonConditionResponse> findWebtoonsByWebtoonCondition(
        String condition) {
        return webtoonRepository.findPopularWebtoonsByCondition(condition);
    }
}
