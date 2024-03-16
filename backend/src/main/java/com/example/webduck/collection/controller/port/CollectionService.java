package com.example.webduck.collection.controller.port;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import java.util.List;

public interface CollectionService {

    List<Collection> findAll();
    Collection create(SessionMember sessionMember, CollectionCreate collectionCreate);
    List<Collection> findMyCollectionsByMember(Long id, SessionMember sessionMember);

}
