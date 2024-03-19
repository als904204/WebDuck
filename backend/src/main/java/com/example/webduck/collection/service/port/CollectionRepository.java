package com.example.webduck.collection.service.port;

import com.example.webduck.collection.domain.Collection;
import java.util.List;
import java.util.Optional;

public interface CollectionRepository {

    List<Collection> findAll();

    Optional<Collection> findById(Long id);

    Collection save(Collection collection);

    List<Collection> findCollectionsByOwnerId(Long id);

    Optional<Collection> findCollectionByOwnerId(Long id);

    void deleteById(Long id);


}
