package com.example.webduck.collection.infrastructure;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.service.port.CollectionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CollectionRepositoryImpl implements CollectionRepository {

    private final CollectionJpaRepository collectionJpaRepository;

    @Override
    public List<Collection> findAll() {
        return collectionJpaRepository.findAll().stream()
            .map(CollectionEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Collection> findById(Long id) {
        return collectionJpaRepository.findById(id).map(CollectionEntity::toModel);
    }

    @Override
    public Collection save(Collection collection) {
        CollectionEntity entity = collectionJpaRepository.save(CollectionEntity.from(collection));
        return entity.toModel();
    }

    @Override
    public List<Collection> findByOwnerId(Long id) {
        List<CollectionEntity> entities = collectionJpaRepository.findByOwnerId(id);
        return entities.stream().map(CollectionEntity::toModel).collect(Collectors.toList());
    }

}
