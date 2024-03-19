package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WebtoonRepositoryImpl implements WebtoonRepository {

    private final WebtoonJpaRepository webtoonJpaRepository;

    @Override
    public Optional<Webtoon> findById(Long Id) {
        Optional<WebtoonEntity> entity = webtoonJpaRepository.findById(Id);
        return entity.map(WebtoonEntity::toModel);
    }

    @Override
    public List<Webtoon> findAll() {
        List<WebtoonEntity> entities = webtoonJpaRepository.findAll();
        return entities.stream().map(WebtoonEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames) {
        return webtoonJpaRepository.findWebtoonsByGenres(genreNames);
    }

    @Override
    public List<WebtoonPopularResponse> findPopularWebtoonsByCondition(
        WebtoonSortCondition condition) {
        return webtoonJpaRepository.findPopularWebtoonsByCondition(condition);
    }

    @Override
    public Webtoon save(Webtoon webtoon) {
        WebtoonEntity entity = webtoonJpaRepository.save(WebtoonEntity.from(webtoon));
        return entity.toModel();
    }

    @Override
    public boolean existsById(Long id) {
        return webtoonJpaRepository.existsById(id);
    }

    @Override
    public Webtoon getById(Long id) {
        return findById(id).orElseThrow(()-> new CustomException(
            LogicExceptionCode.WEBTOON_NOT_FOUND));
    }

    @Override
    public List<Webtoon> findWebtoonsByPublishDay(PublishDay publishDay) {
        List<WebtoonEntity> entities = webtoonJpaRepository.findWebtoonsByPublishDay(
            publishDay);
        return entities.stream().map(WebtoonEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Webtoon> findWebtoonsByPlatform(Platform platform) {
        List<WebtoonEntity> entities = webtoonJpaRepository.findWebtoonsByPlatform(
            platform);
        return entities.stream().map(WebtoonEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Webtoon> findAllByIdIn(List<Long> webtoonIds) {
        List<WebtoonEntity> entities = webtoonJpaRepository.findAllByIdIn(webtoonIds);
        return entities.stream().map(WebtoonEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Webtoon> findByCollectionId(Long id) {
        List<WebtoonEntity> entities = webtoonJpaRepository.findWebtoonsByCollectionId(
            id);

        return entities.stream()
            .map(WebtoonEntity::toModel)
            .collect(Collectors.toList());
    }


}
