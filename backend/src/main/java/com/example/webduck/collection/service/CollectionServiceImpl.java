package com.example.webduck.collection.service;

import com.example.webduck.collection.controller.port.CollectionService;
import com.example.webduck.collection.controller.response.CollectionDetailResponse;
import com.example.webduck.collection.controller.response.CollectionDetailResponse.WebtoonInfo;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionUpdate;
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
    @Transactional(readOnly = true)
    public List<Collection> findAll(SessionMember sessionMember) {
        List<Collection> collections = collectionRepository.findAll();

        if (sessionMember != null) {
            Member member = memberRepository.getById(sessionMember.getId());
            Collection.flagAuthor(member, collections);
        }

        return collections;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection findById(Long id) {
        return collectionRepository.findById(id).orElseThrow(() -> new CustomException(
            LogicExceptionCode.COLLECTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public Collection create(SessionMember sessionMember, CollectionCreate collectionCreate) {

        // Member 검증
        Long memberId = sessionMember.getId();
        Member member = memberRepository.getById(memberId);

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
                .collectionId(collection.getId())
                .build();

            collectionWebtoons.add(colWebtoons);
        }

        collectionWebtoonsRepository.saveAll(collectionWebtoons);
        return collection;
    }

    @Override
    @Transactional
    public Collection update(SessionMember sessionMember, CollectionUpdate collectionUpdate,
        Long collectionId) {

        Collection collection = collectionRepository.findById(collectionId)
            .orElseThrow(() -> {
                log.error("Collection not found with ID: {}", collectionId);
                return new CustomException(LogicExceptionCode.COLLECTION_NOT_FOUND);
            });

        Member member = memberRepository.getById(sessionMember.getId());
        // 웹툰 ID 검증
        List<Long> reqWebtoonIds = collectionUpdate.getWebtoonIds();
        int actualSize = webtoonRepository.findAllByIdIn(reqWebtoonIds).size();
        int reqWebtoonSize = collectionUpdate.getWebtoonIds().size();
        Webtoon.validateSizeMismatch(actualSize, reqWebtoonSize);


        // 작성자 검증,업데이트 후 저장
        collection.validateOwner(member, collection);
        collection = collection.update(collectionUpdate);
        collection = collectionRepository.save(collection);

        collectionWebtoonsRepository.deleteAllByCollectionId(collectionId);

        List<CollectionWebtoons> collectionWebtoons = new ArrayList<>(reqWebtoonSize);

        for (Long webtoonId : collectionUpdate.getWebtoonIds()) {
            CollectionWebtoons colWebtoons = CollectionWebtoons.builder()
                .ownerId(member.getId())
                .webtoonId(webtoonId)
                .collectionId(collection.getId())
                .build();
            collectionWebtoons.add(colWebtoons);
        }

        collectionWebtoonsRepository.saveAll(collectionWebtoons);

        return collection;
    }



    @Transactional(readOnly = true)
    @Override
    public CollectionDetailResponse findMyCollection(Long collectionId, SessionMember sessionMember) {
        Member member = memberRepository.getById(sessionMember.getId());

        Collection collection = collectionRepository.findById(collectionId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.COLLECTION_NOT_FOUND));
        collection.validateOwner(member, collection);

        List<Webtoon> webtoons = webtoonRepository.findByCollectionId(collection.getId());

        List<WebtoonInfo> webtoonInfoList = new ArrayList<>();
        for (Webtoon webtoon : webtoons) {
            webtoonInfoList.add(new WebtoonInfo(webtoon.getId(), webtoon.getTitle()));
        }

        return CollectionDetailResponse.from(collection, webtoonInfoList);
    }

    @Override
    @Transactional
    public void deleteById(SessionMember sessionMember, Long id) {
        Collection collection = collectionRepository.findById(id)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.COLLECTION_NOT_FOUND));

        Member member = memberRepository.getById(sessionMember.getId());
        collection.validateOwner(member, collection);

        log.info("{} has deleted collection with id={}", member.getUsername(), collection.getId());
        collectionRepository.deleteById(id);
    }


}
