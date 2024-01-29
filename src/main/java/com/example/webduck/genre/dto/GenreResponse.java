package com.example.webduck.genre.dto;

import com.example.webduck.genre.entity.Genre;
import lombok.Getter;

@Getter
public class GenreResponse {

    private final String genreName;

    public GenreResponse(Genre genre) {
        this.genreName = genre.getName();
    }


}
