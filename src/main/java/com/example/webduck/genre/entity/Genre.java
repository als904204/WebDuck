package com.example.webduck.genre.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Genre{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    public Genre(String type) {
        this.type = type;
    }

    protected Genre() {}

    public String getType() {
        return type;
    }
}
