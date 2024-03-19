package com.example.webduck.collection.controller.port;

import com.example.webduck.collection.controller.response.CollectionDetailResponse;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionUpdate;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import java.util.List;

public interface CollectionService {

    // 모든 보관함 조회
    List<Collection> findAll(SessionMember member);

    // ID로 보관함 조회
    Collection findById(Long id);

    // 보관함 생성
    Collection create(SessionMember sessionMember, CollectionCreate collectionCreate);

    // 보관함 수정
    Collection update(SessionMember sessionMember, CollectionUpdate collectionUpdate, Long collectionId);

    // 내 보관함 ID 로 조회
    CollectionDetailResponse findMyCollection(Long collectionId, SessionMember sessionMember);

    // 보관함 삭제
    void deleteById(SessionMember member, Long id);
}
