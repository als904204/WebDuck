package com.example.webduck.genre.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Genre{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GenreType genreType;

    public Genre(GenreType genreType) {
        this.genreType = genreType;
    }

    protected Genre() {}

    public GenreType getGenreType() {
        return genreType;
    }
}
