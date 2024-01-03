package com.example.webduck.genre.dto;

import com.example.webduck.genre.entity.Genre;
import lombok.Getter;

@Getter
public class GenreResponse {

    private final String genreType;

    public GenreResponse(Genre genre) {
        this.genreType = genre.getType();
    }


}
