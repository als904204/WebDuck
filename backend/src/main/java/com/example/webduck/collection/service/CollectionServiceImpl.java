package com.example.webduck.collection.service;

import com.example.webduck.collection.controller.port.CollectionService;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionWebtoons;
import com.example.webduck.collection.service.port.CollectionRepository;
import com.example.webduck.collection.service.port.CollectionWebtoonsRepository;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.service.port.MemberRepository;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Builder
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CollectionWebtoonsRepository collectionWebtoonsRepository;


    @Override
    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    @Override
    @Transactional
    public Collection create(SessionMember sessionMember, CollectionCreate collectionCreate) {

        // Member 검증
        Long memberId = sessionMember.getId();
        Member member = memberRepository.getById(memberId);
        String username = member.getUsername();
        // 요청한 웹툰ID 들이 DB에 있는지 검증
        // DB actual webtoons size : req webtoons size 검증
        List<Long> webtoonIds = collectionCreate.getWebtoonIds();
        int actualSize = webtoonRepository.findAllByIdIn(webtoonIds).size();
        int reqWebtoonSize = collectionCreate.getWebtoonIds().size();
        Webtoon.validateSizeMismatch(actualSize, reqWebtoonSize);

        // 컬렉션 생성
        Collection collection = Collection.from(member, collectionCreate);
        collection = collectionRepository.save(collection);

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

    @Override
    public List<Collection> findMyCollectionsByMember(Long id, SessionMember sessionMember) {

        Member member = memberRepository.getById(sessionMember.getId());
        if (!id.equals(sessionMember.getId())) {
            log.error("collections by member : Member ID in request path ({}) does not match authenticated member ID ({})", id, sessionMember.getId());
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }

        List<Collection> collections = collectionRepository.findByOwnerId(member.getId());


        return collections;
    }


}
