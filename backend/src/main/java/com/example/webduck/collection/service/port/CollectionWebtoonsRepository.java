package com.example.webduck.collection.service.port;

import com.example.webduck.collection.infrastructrue.CollectionWebtoons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionWebtoonsRepository extends JpaRepository<CollectionWebtoons,Long> {

}
