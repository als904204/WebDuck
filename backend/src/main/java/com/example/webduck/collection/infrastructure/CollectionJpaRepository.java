package com.example.webduck.collection.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionJpaRepository extends JpaRepository<CollectionEntity, Long> {

    // 회원 ID 모든 보관함 조회
    List<CollectionEntity> findCollectionsByOwnerId(Long id);

    // 회원 ID 보관함 단건 조회
    Optional<CollectionEntity> findCollectionByOwnerId(Long id);
}
