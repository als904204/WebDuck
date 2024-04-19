package com.example.webduck.webtoon.service;

import com.example.webduck.admin.controller.request.WebtoonApiRequest;
import com.example.webduck.admin.controller.response.WebtoonMergeResponse;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.genre.repository.WebtoonGenreRepository;
import com.example.webduck.genre.service.GenreMapping;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.service.port.MemberRepository;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.KyuAsyncApiClient;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.KoreaApiClient;
import com.example.webduck.webtoon.service.port.KoreaWebtoonResponse;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import com.example.webduck.webtoon.service.port.KoreaWebtoonResponse.WebtoonKor;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse.WebtoonKyu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonMergeService {

    private final KoreaApiClient koreaWebtoonApiClient;
    private final WebtoonRepository webtoonRepository;
    private final GenreRepository genreRepository;
    private final WebtoonGenreRepository webtoonGenreRepository;
    private final MemberRepository memberRepository;
    private final KyuAsyncApiClient kyuAsyncApiClient;

    @Transactional
    public WebtoonMergeResponse mergeAndSave(WebtoonApiRequest request, SessionMember sessionMember) {
        Member member = memberRepository.findByIdAneUsername(sessionMember.getId(),
                sessionMember.getUsername())
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        Member.validateAdmin(member);

        Platform platform = request.getPlatform();

        CompletableFuture<Map<String, WebtoonKyu>> kyuWebtoonsAsync = kyuAsyncApiClient.getWebtoonsAsync(
            platform);

        KoreaWebtoonResponse koreaWebtoons = koreaWebtoonApiClient.getWebtoons(platform);

        // getWebtoonsAsync 완료되면 실행
        return kyuWebtoonsAsync.thenApply(
            kyuWebtoons -> {
                // 데이터 통합 및 저장
                List<Webtoon> mergedWebtoons = mergeWebtoons(koreaWebtoons, kyuWebtoons);
                mergedWebtoons = webtoonRepository.saveAll(mergedWebtoons);
                saveMappedGenres(mergedWebtoons, kyuWebtoons);
                log.info("Successfully merged {} webtoons with genres for platform: {}",
                    mergedWebtoons.size(), platform);
                return new WebtoonMergeResponse(mergedWebtoons.size(), platform);
            }).join();
    }

    // korea 웹툰 <=> 만화 규장각 웹툰 title 같을 경우 merge
    private List<Webtoon> mergeWebtoons(KoreaWebtoonResponse webtoonKorea,
        Map<String, WebtoonKyu> kyuWebtoons) {
        log.info("merge webtoons");
        List<Webtoon> mergedWebtoons = new ArrayList<>();

        for (WebtoonKor webtoonKor : webtoonKorea.getWebtoons()) {

            Webtoon webtoon = Webtoon.fromWebtoonKor(webtoonKor);

            if (kyuWebtoons.containsKey(webtoonKor.getTitle())) {
                WebtoonKyu webtoonKyu = kyuWebtoons.get(webtoonKor.getTitle());
                webtoon = webtoon.merge(webtoonKyu);
                mergedWebtoons.add(webtoon);
            }
        }
        return mergedWebtoons;
    }

    // merge 된 웹툰의 장르를 만화 규장각에서 받아온 장르를 변환하여 저장한다.
    private void saveMappedGenres(List<Webtoon> mergedWebtoons,
        Map<String, WebtoonKyu> kyuWebtoons) {
        log.info("starting mapping genres");
        for (Webtoon webtoon : mergedWebtoons) {

            WebtoonKyu webtoonKyu = kyuWebtoons.get(webtoon.getTitle());

            // API 응답온 장르 DB 에 맞게 변환 후 저장
            List<String> mappedGenres = GenreMapping.getMappedGenres(webtoonKyu.getMainGenreCdNm());

            for (String genreName : mappedGenres) {
                Genre genre = genreRepository.findByName(genreName)
                    .orElseThrow(() -> {
                        log.error("Invalid genre name: {}", genreName);
                        return new CustomException(ValidationExceptionCode.INVALID_GENRE_NAME);
                    });

                WebtoonGenre webtoonGenre = new WebtoonGenre();
                webtoonGenre.setGenre(genre);
                webtoonGenre.setWebtoon(webtoon);

                webtoonGenreRepository.save(webtoonGenre);
            }
        }

    }




}
