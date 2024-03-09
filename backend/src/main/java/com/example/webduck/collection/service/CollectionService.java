package com.example.webduck.collection.service;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.infrastructure.entity.CollectionWebtoons;
import com.example.webduck.collection.service.port.CollectionRepository;
import com.example.webduck.collection.service.port.CollectionWebtoonsRepository;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.repository.MemberRepository;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CollectionWebtoonsRepository collectionWebtoonsRepository;


    @Transactional
    public Collection createCollection(SessionMember sessionMember, CollectionCreate collectionCreate) {

        // Member 검증
        Long memberId = sessionMember.getId();
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        // DB actual webtoons size : req webtoons size 검증
        List<Long> webtoonIds = collectionCreate.getWebtoonIds();
        int actualSize = webtoonRepository.findAllByIdIn(webtoonIds).size();
        int reqWebtoonSize = collectionCreate.getWebtoonIds().size();
        Webtoon.validateSizeMismatch(actualSize, reqWebtoonSize);

        // 컬렉션 생성
        Collection collection = Collection.from(member, collectionCreate);
        collectionRepository.save(collection);

        // 컬렉션 웹툰 생성
        List<CollectionWebtoons> collectionWebtoons = new ArrayList<>(reqWebtoonSize);

        for (Long webtoonId : collectionCreate.getWebtoonIds()) {
            CollectionWebtoons colWebtoons = CollectionWebtoons.builder()
                .ownerId(memberId)
                .webtoonId(webtoonId)
                .build();
            collectionWebtoons.add(colWebtoons);
        }
        collectionWebtoonsRepository.saveAll(collectionWebtoons);

        return collection;
    }


}
