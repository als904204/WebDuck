<template>
  <!-- 장르 종류-->
  <div>
    <div class="button-container">
      <Button v-for="genre in genres" :key="genre.genreName" :label="genre.genreName" class="p-button" />
    </div>
    <FetchWebtoonList :genreNames="['ACTION', 'DRAMA']" serviceType="GENRES" />
  </div>

</template>

<script setup>
import {getGenres, getWebtoonsByGenres} from "../../service/WebtoonListFetchService.js";
import {onMounted, ref} from "vue";
import FetchWebtoonList from "./list/WebtoonList.vue";

const genresToKorean = {
  "ACTION": "액션",
  "ADULT": "성인",
  "DAILYLIFE": "일상",
  "DRAMA": "드라마",
  "FANTASY": "판타지",
  "GAG": "개그",
  "MARTIALARTS": "무협",
  "ROMANCE": "로맨스",
  "SPORTS": "스포츠",
  "THRILLER": "스릴러"
};

const genres = ref([]);

onMounted(async () => {
  try {
    const apiResponse = await getGenres();
    genres.value = apiResponse.map(genre => ({
      genreName: genresToKorean[genre.genreName] || genre.genreName
    }));
  } catch (error) {
    console.error("error while request genres:", error);
  }
});


</script>

<style scoped>
.button-container {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  justify-content: center;
}
</style>
