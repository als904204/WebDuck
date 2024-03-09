package com.example.webduck.collection.service.port;

import com.example.webduck.collection.domain.Collection;
import java.util.Optional;

public interface CollectionRepository {

    Optional<Collection> findById(Long id);

    Collection save(Collection post);

}
