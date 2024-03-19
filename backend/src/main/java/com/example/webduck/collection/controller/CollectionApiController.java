package com.example.webduck.collection.controller;

import com.example.webduck.collection.controller.port.CollectionService;
import com.example.webduck.collection.controller.response.CollectionDetailResponse;
import com.example.webduck.collection.controller.response.CollectionResponse;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionUpdate;
import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    // 모든 보관함 조회
    @GetMapping
    public ResponseEntity<List<CollectionResponse>> findAll(@LoginMember SessionMember member) {
        List<CollectionResponse> response = collectionService.findAll(member).stream()
            .map(CollectionResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 보관함 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponse> findById(@PathVariable Long id){
        CollectionResponse response = CollectionResponse.from(collectionService.findById(id));
        return ResponseEntity.ok(response);
    }


    // 보관함 생성
    @PostMapping
    public ResponseEntity<Void> create(@LoginMember SessionMember member,
        @Valid  @RequestBody CollectionCreate collectionCreate) {

        collectionService.create(member, collectionCreate);
        return ResponseEntity.noContent().build();
    }

    // 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@LoginMember SessionMember member, @RequestBody
    CollectionUpdate collectionUpdate, @PathVariable Long id) {
        collectionService.update(member, collectionUpdate, id);
        return ResponseEntity.noContent().build();
    }

    // 보관함 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,@LoginMember SessionMember member) {
        collectionService.deleteById(member, id);
        return ResponseEntity.noContent().build();
    }

    // 내 보관함 단건 조회
    @GetMapping("/{id}/member")
    public ResponseEntity<CollectionDetailResponse> findMyCollection(@PathVariable Long id,
        @LoginMember SessionMember sessionMember) {
        CollectionDetailResponse response = collectionService.findMyCollection(id,
            sessionMember);
        return ResponseEntity.ok(response);
    }

}
