package com.example.webduck.collection.infrastructure;

import com.example.webduck.collection.domain.CollectionWebtoons;
import com.example.webduck.collection.service.port.CollectionWebtoonsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CollectionWebtoonsRepositoryImpl implements CollectionWebtoonsRepository {

    private final CollectionWebtoonsJpaRepository collectionWebtoonsJpaRepository;

    @Override
    public List<CollectionWebtoons> saveAll(List<CollectionWebtoons> collectionWebtoons) {
        List<CollectionWebtoonsEntity> entities = collectionWebtoons.stream()
            .map(CollectionWebtoonsEntity::from)
            .collect(Collectors.toList());

        return collectionWebtoonsJpaRepository.saveAll(entities).stream()
            .map(CollectionWebtoonsEntity::toModel)
            .collect(Collectors.toList());
    }

}
