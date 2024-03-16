package com.example.webduck.collection.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionJpaRepository extends JpaRepository<CollectionEntity,Long> {
    List<CollectionEntity> findByOwnerId(Long id);
}
