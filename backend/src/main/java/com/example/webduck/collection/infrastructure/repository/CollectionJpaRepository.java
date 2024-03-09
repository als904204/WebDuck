package com.example.webduck.collection.infrastructure.repository;

import com.example.webduck.collection.infrastructure.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionJpaRepository extends JpaRepository<CollectionEntity,Long> {

}
