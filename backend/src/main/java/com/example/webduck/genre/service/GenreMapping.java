package com.example.webduck.genre.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreMapping {

    private static final Map<String, List<String>> genreMapping;

    static {
        genreMapping = new HashMap<>();
        genreMapping.put("액션", Collections.singletonList("ACTION"));
        genreMapping.put("일상", Collections.singletonList("DAILYLIFE"));
        genreMapping.put("드라마", Collections.singletonList("DRAMA"));
        genreMapping.put("로맨스판타지", Arrays.asList("ROMANCE", "FANTASY"));
        genreMapping.put("판타지", Collections.singletonList("FANTASY"));
        genreMapping.put("코믹", Collections.singletonList("GAG"));
        genreMapping.put("순정", Collections.singletonList("ROMANCE"));
        genreMapping.put("소년", Collections.singletonList("PURE"));
        genreMapping.put("무협", Collections.singletonList("MARTIALARTS"));
        genreMapping.put("추리미스터리", Collections.singletonList("THRILLER"));
        genreMapping.put("공포", Collections.singletonList("MYSTERY"));
        genreMapping.put("스포츠", Collections.singletonList("SPORTS"));
    }

    public static List<String> getMappedGenres(String apiGenre) {
        return genreMapping.getOrDefault(apiGenre, Collections.emptyList());
    }


}
