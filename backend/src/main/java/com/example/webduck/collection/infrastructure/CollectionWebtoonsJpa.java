package com.example.webduck.collection.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionWebtoonsJpa extends JpaRepository<CollectionWebtoonsEntity,Long>{

    void deleteAllByCollectionId(Long id);

    List<CollectionWebtoonsEntity> findByCollectionId(Long id);
}
