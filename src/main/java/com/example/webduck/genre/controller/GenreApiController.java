package com.example.webduck.genre.controller;

import com.example.webduck.genre.dto.GenreResponse;
import com.example.webduck.genre.service.GenreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
@RestController
public class GenreApiController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getGenreList() {
        List<GenreResponse> genreList = genreService.findAllGenres();
        return ResponseEntity.ok(genreList);
    }

    @PostMapping
    public ResponseEntity<Void> saveGenre(String type) {
        genreService.saveGenreType(type);
        return ResponseEntity.noContent().build();
    }

}
