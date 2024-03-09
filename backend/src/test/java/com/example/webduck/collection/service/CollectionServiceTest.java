package com.example.webduck.collection.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.collection.mock.FakeCollectionRepository;
import org.junit.jupiter.api.BeforeEach;


class CollectionServiceTest {


    @BeforeEach
    void init() {
        FakeCollectionRepository fakeCollectionRepository = new FakeCollectionRepository();
    }

}