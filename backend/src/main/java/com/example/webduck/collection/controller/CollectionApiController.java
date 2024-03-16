package com.example.webduck.collection.controller;

import com.example.webduck.collection.controller.port.CollectionService;
import com.example.webduck.collection.controller.response.CollectionResponse;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/collection")
@RequiredArgsConstructor
@RestController
public class CollectionApiController {


    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<CollectionResponse>> findAll() {
        List<CollectionResponse> response = collectionService.findAll().stream()
            .map(CollectionResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Void> create(@LoginMember SessionMember member,
        @RequestBody CollectionCreate collectionCreate) {

        collectionService.create(member, collectionCreate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Collection>> findByOwnerId(@PathVariable Long id, @LoginMember SessionMember member) {
        List<Collection> collectionsByMember = collectionService.findMyCollectionsByMember(id,
            member);
        return ResponseEntity.ok(collectionsByMember);
    }

}
