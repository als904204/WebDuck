package com.example.webduck.collection.mock;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.service.port.CollectionRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeCollectionRepository implements CollectionRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);

    // 리스트를 동기화
    //  멀티스레드 환경에서 안전하게 사용
    private final List<Collection> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<Collection> findById(Long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Collection save(Collection collection) {
        if (collection.getId() == null || collection.getId() == 0) {
            Collection newCollection = Collection.builder()
                .id(autoGeneratedId.incrementAndGet())
                .title(collection.getTitle())
                .description(collection.getDescription())
                .isPublic(collection.isPublic())
                .ownerId(collection.getOwnerId())
                .ownerName(collection.getOwnerName())
                .build();

            data.add(newCollection);
            return newCollection;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), collection.getId()));
            data.add(collection);
            return collection;
        }
    }

}
