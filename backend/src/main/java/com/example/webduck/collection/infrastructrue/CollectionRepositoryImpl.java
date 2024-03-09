package com.example.webduck.collection.infrastructrue;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.service.port.CollectionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CollectionRepositoryImpl implements CollectionRepository {

    private final CollectionJpaRepository collectionJpaRepository;
    @Override
    public Optional<Collection> findById(Long id) {
        return collectionJpaRepository.findById(id).map(CollectionEntity::toModel);
    }

    @Override
    public Collection save(Collection collection) {
        CollectionEntity entity = collectionJpaRepository.save(CollectionEntity.from(collection));
        return entity.toModel();
    }
}
