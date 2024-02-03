<template>
  <!-- 장르 버튼-->
  <div class="button-container">
      <Button v-for="genre in genres" :key="genre.genreName"
              :label="genre.genreName" class="p-button"
              :class="{ 'selected': selectedGenres.includes(genre.genreName) }"
              @click="toggleGenre(genre.genreName)" />
  </div>
  <div class="webtoon-container">

    <div v-if="message">
      <div v-if="messageType === 'error'">
        <Message severity="error">{{ message }}</Message>
      </div>
      <div v-else-if="messageType === 'warning'">
        <Message severity="warn">{{ message }}</Message>
      </div>
    </div>

    <div class="webtoon-grid">
      <div class="webtoon-item" v-for="webtoon in webtoons" :key="webtoon.id">
        <router-link :to="`/review/${webtoon.id}`">
          <img class="webtoon-image" :src="webtoon.imagePath" :alt="webtoon.title">
        </router-link>
        <p class="webtoon-title">{{ webtoon.title }}</p>
      </div>
    </div>
  </div>

</template>


<script setup>
import {getGenres, getWebtoonsByGenres} from "../../service/WebtoonListFetchService.js";
import {onMounted, ref} from "vue";
import Message from 'primevue/message';

const engGenresToKorean = {
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

const korGenresToEng = {
  "액션": "ACTION",
  "성인": "ADULT",
  "일상": "DAILYLIFE",
  "드라마": "DRAMA",
  "판타지": "FANTASY",
  "개그": "GAG",
  "무협": "MARTIALARTS",
  "로맨스": "ROMANCE",
  "스포츠": "SPORTS",
  "스릴러": "THRILLER"
};

const genres = ref([]);
const selectedGenres = ref([]); // 여러 장르를 선택할 수 있도록 배열로 관리
const message = ref('');
const messageType = ref('');
let webtoons = ref([]);

// 장르 목록 가져오기
onMounted(async () => {
  try {
    const response = await getGenres();
    genres.value = response.map(genre => ({
      genreName: engGenresToKorean[genre.genreName] || genre.genreName
    }));
    if (genres.value.length > 0) {
      const initialGenre = genres.value[0].genreName;
      await toggleGenre(initialGenre);
    }
  } catch (error) {
    console.error("error while request genres:", error);
  }
});



const toggleGenre = async (genreName) => {
  const genreKey = korGenresToEng[genreName] || genreName;
  const index = selectedGenres.value.findIndex(g => korGenresToEng[g] === genreKey);

  if (index > -1) {
    // 이미 선택된 장르면 제거
    selectedGenres.value.splice(index, 1);
  } else {
    // 선택되지 않은 장르면 추가
    selectedGenres.value.push(genreName);
  }
  try {
    // 선택된 장르(영어로 변환된)로 웹툰 목록 요청
    webtoons.value = await getWebtoonsByGenres(selectedGenres.value.map(g => korGenresToEng[g]));
    message.value = '';
    messageType.value = '';
  } catch (error) {
    if (error.response && error.response.status === 404) {
      message.value = '찾으시는 웹툰이 없어요! 장르를 다시 선택해 주세요';
      webtoons.value = [];
      messageType.value = 'warning' ;
    } else if (error.response && error.response.status === 400) {
      message.value = '선택된 장르가 없습니다. 장르를 선택해 주세요';
      webtoons.value = [];
      messageType.value = 'error';
    } else {
      message.value = '알수없는 오류가 발생했습니다.';
      webtoons.value = [];
      messageType.value = 'error';
    }
  }
};

</script>

<style scoped>
.button-container {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  justify-content: center;
  margin-bottom : 16px;
}

/* 선택된 버튼에 적용할 스타일 */
.selected {
  background-color: #007bff; /* 예시 색상 */
  color: white;
}


.webtoon-grid {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap:10px;
}

.webtoon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.webtoon-item a {
  text-decoration: none;
  color: inherit;
}

.webtoon-image {
  width: 200px;
  height: 300px;
  object-fit: cover;
}

.webtoon-title{
  white-space: nowrap;
  max-width: 100%;
}

/* 화면 크기에 따라 열의 개수를 조정 */
@media (max-width: 1200px) { /* 데스크탑 크기 */
  .webtoon-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 992px) { /* 태블릿 크기 */
  .webtoon-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) { /* 중간 크기의 태블릿 */
  .webtoon-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 576px) { /* 모바일 크기 */
  .webtoon-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}


</style>
