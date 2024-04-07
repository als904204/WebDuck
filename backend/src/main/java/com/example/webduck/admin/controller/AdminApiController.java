package com.example.webduck.admin.controller;


import com.example.webduck.admin.controller.request.WebtoonApiRequest;
import com.example.webduck.admin.controller.response.WebtoonDelete;
import com.example.webduck.admin.controller.response.WebtoonMergeResponse;
import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;

import com.example.webduck.webtoon.controller.port.WebtoonService;
import com.example.webduck.webtoon.service.WebtoonMergeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final WebtoonMergeService webtoonMergeService;
    private final WebtoonService webtoonService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/webtoon")
    public ResponseEntity<WebtoonMergeResponse> uploadWebtoon(
        @LoginMember SessionMember sessionMember,
        @RequestBody WebtoonApiRequest request) {

        WebtoonMergeResponse webtoonMergeResponse = webtoonMergeService.mergeAndSave(request,
            sessionMember);

        return ResponseEntity.ok(webtoonMergeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/webtoon")
    public ResponseEntity<Long> deleteDuplicateWebtoons() {
        long response = webtoonService.deleteDuplicateWebtoons();
        return ResponseEntity.ok(response);
    }
}


