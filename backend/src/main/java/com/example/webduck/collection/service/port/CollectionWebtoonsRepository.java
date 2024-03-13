package com.example.webduck.collection.service.port;

import com.example.webduck.collection.infrastructure.CollectionWebtoons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionWebtoonsRepository extends JpaRepository<CollectionWebtoons,Long> {

}
