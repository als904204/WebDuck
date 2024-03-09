package com.example.webduck.collection.controller;

import com.example.webduck.collection.controller.response.CollectionResponse;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.service.CollectionService;
import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor
@RestController
public class CollectionApiController {


    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<CollectionResponse> create(@LoginMember SessionMember member,
        @RequestBody CollectionCreate collectionCreate) {

        CollectionResponse response = CollectionResponse.from(
            collectionService.createCollection(member, collectionCreate));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

}
