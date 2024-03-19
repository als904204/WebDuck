package com.example.webduck.collection.service.port;

import com.example.webduck.collection.domain.CollectionWebtoons;
import java.util.List;

public interface CollectionWebtoonsRepository {

    void saveAll(List<CollectionWebtoons> collectionWebtoons);

    void deleteAllByCollectionId(Long id);

    // Collection ID 로 CollectionWebtoons 조회
    List<CollectionWebtoons> findByCollectionId(Long id);
}
