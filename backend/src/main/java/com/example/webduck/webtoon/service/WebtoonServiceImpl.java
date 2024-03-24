package com.example.webduck.webtoon.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.service.port.ReviewRepository;
import com.example.webduck.webtoon.controller.port.WebtoonService;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.controller.response.WebtoonDetails;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.controller.response.WebtoonResponse;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonSortCondition;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Builder
@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonServiceImpl implements WebtoonService {

    private final WebtoonRepository webtoonRepository;

    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public WebtoonDetails getWebtoonDetails(Long id) {

        Webtoon webtoon = webtoonRepository.getById(id);

        List<Review> reviews = reviewRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(
            id);

        Double webtoonRating = Review.calculateRatingAvg(reviews);
        int reviewCount = reviews.size();

        return new WebtoonDetails(webtoon,reviewCount,webtoonRating);
    }
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findAll() {
        List<Webtoon> webtoons = webtoonRepository.findAll();
        return webtoons.stream().map(WebtoonResponse::from)
            .collect(Collectors.toList());
    }

    // 요청에 따른 요일 웹툰 목록 조회 (MONDAY,SUNDAY..)
    @Transactional(readOnly = true)
    @Cacheable(value = "findWebtoonsByPublishDayCache", key = "#publishDay", condition = "#publishDay == T(com.example.webduck.webtoon.infrastructure.PublishDay).MONDAY")
    public List<WebtoonResponse> findWebtoonsByPublishDay(PublishDay publishDay) {

        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPublishDay(publishDay);

        return webtoons.stream().map(WebtoonResponse::from)
            .collect(Collectors.toList());
    }

    // 요청에 따른 플랫폼 별 웹툰 목록 조회 (NAVER,KAKAO..)
    @Cacheable(value = "findWebtoonsByPlatformCache", key = "#platform", condition = "#platform == T(com.example.webduck.webtoon.infrastructure.Platform).KAKAO")
    @Transactional(readOnly = true)
    public List<WebtoonResponse> findWebtoonsByPlatform(Platform platform) {
        List<Webtoon> webtoons = webtoonRepository.findWebtoonsByPlatform(platform);
        return webtoons.stream()
            .map(WebtoonResponse::from).collect(Collectors.toList());
    }

    // 장르 요청에 따른 웹툰 목록 조회 (ex : 무협,개그,판타지 장르를 포함한 웹툰)
    @Transactional(readOnly = true)
    public List<WebtoonGenreResponse> findWebtoonsByGenreNames(List<String> genreNames) {
        List<WebtoonGenreResponse> webtoonsByGenres = webtoonRepository.findWebtoonsByGenres(
            genreNames);

        if (webtoonsByGenres.isEmpty()) {
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

    @Override
    public List<Webtoon> findByCollectionId(Long id) {
        return webtoonRepository.findByCollectionId(id);
    }
}
