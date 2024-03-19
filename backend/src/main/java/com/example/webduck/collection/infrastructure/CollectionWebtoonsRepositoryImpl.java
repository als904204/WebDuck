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

    private final CollectionWebtoonsJpa collectionWebtoonsJpaRepository;

    @Override
    public void saveAll(List<CollectionWebtoons> collectionWebtoons) {
        List<CollectionWebtoonsEntity> entities = collectionWebtoons.stream()
            .map(CollectionWebtoonsEntity::from)
            .collect(Collectors.toList());

        collectionWebtoonsJpaRepository.saveAll(entities).stream()
            .map(CollectionWebtoonsEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByCollectionId(Long id) {
        collectionWebtoonsJpaRepository.deleteAllByCollectionId(id);
    }

    @Override
    public List<CollectionWebtoons> findByCollectionId(Long id) {
        List<CollectionWebtoonsEntity> entities = collectionWebtoonsJpaRepository.findByCollectionId(
            id);

        return entities.stream()
            .map(CollectionWebtoonsEntity::toModel)
            .collect(Collectors.toList());
    }

}
