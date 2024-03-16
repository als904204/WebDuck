package com.example.webduck.collection.service.port;

import com.example.webduck.collection.domain.CollectionWebtoons;
import java.util.List;

public interface CollectionWebtoonsRepository {

    List<CollectionWebtoons> saveAll(List<CollectionWebtoons> collectionWebtoons);


}
