package com.example.webduck.genre.entity;

import com.example.webduck.global.common.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Genre extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    protected Genre() {}

    public String getName() {
        return name;
    }
}
