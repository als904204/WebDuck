package com.example.webduck.webtoon.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import com.example.webduck.webtoon.dto.WebtoonDetails;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonPopularResponse;
import com.example.webduck.webtoon.dto.WebtoonResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.entity.WebtoonSortCondition;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;

    private final ReviewRepository reviewRepository;

    /**
     * 웹툰 상세보기
     * @param webtoonId 조회할 웹툰 ID
     * @return 웹툰 정보,리뷰 목록,리뷰 개수,웹툰 리뷰 평균 점수
     */
    @Transactional(readOnly = true)
    public WebtoonDetails getWebtoonDetails(Long webtoonId) {
        Webtoon webtoon = webtoonRepository.findById(webtoonId)
            .orElseThrow(() -> new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND));

        List<Review> reviews = reviewRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(
            webtoonId);

        Double webtoonRating = Review.calculateRatingAvg(reviews);
        int reviewCount = reviews.size();


        return new WebtoonDetails(webtoon,reviewCount,webtoonRating);
    }

    // 모든 웹툰 조회
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonList() {
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream()
            .map(WebtoonResponse::new).collect(Collectors.toList());
    }

    // 요청에 따른 요일 웹툰 목록 조회 (MONDAY,SUNDAY..)
    @Transactional(readOnly = true)
    @Cacheable(value = "findWebtoonsByPublishDayCache", key = "#publishDay", condition = "#publishDay == T(com.example.webduck.webtoon.entity.PublishDay).MONDAY")
    public List<WebtoonResponse> findWebtoonsByPublishDay(PublishDay publishDay) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPublishDay(publishDay);
        return webtoons.stream()
            .map(WebtoonResponse::new).collect(Collectors.toList());
    }

    // 요청에 따른 플랫폼 별 웹툰 목록 조회 (NAVER,KAKAO..)
    @Cacheable(value = "findWebtoonsByPlatformCache", key = "#platform", condition = "#platform == T(com.example.webduck.webtoon.entity.Platform).KAKAO")
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByPlatform(Platform platform) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPlatform(platform);
        return webtoons.stream()
            .map(WebtoonResponse::new).collect(Collectors.toList());
    }

    // 장르 요청에 따른 웹툰 목록 조회 (ex : 무협,개그,판타지 장르를 포함한 웹툰)
    @Transactional(readOnly = true)
    public List<WebtoonGenreResponse> findWebtoonsByGenreNames(List<String> genreNames) {
        List<WebtoonGenreResponse> webtoonsByGenres = webtoonRepository.findWebtoonsByGenres(
            genreNames);
        if (webtoonsByGenres.isEmpty()) {
            log.warn("There are no webtoons in the genre. client genre request ={}", genreNames);
            throw new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND);
        }
        return webtoonsByGenres;
    }

    // 인기 웹툰 조회 (리뷰 개수 순, 평점 순)
    @Transactional(readOnly = true)
    public List<WebtoonPopularResponse> findPopularWebtoonsByCondition(
        WebtoonSortCondition condition) {
        return webtoonRepository.findPopularWebtoonsByCondition(condition);
    }
}
