<template>
  <div class="webtoon-grid">
    <div class="webtoon-item" v-for="webtoon in webtoons" :key="webtoon.id">
      <router-link :to="`/webtoon/${webtoon.id}`">
        <img class="webtoon-image" :src="webtoon.imagePath" :alt="webtoon.title">
      </router-link>
        <p class="webtoon-title">{{ webtoon.title }}</p>
    </div>
  </div>
</template>

<script setup>
import {ref, watch} from 'vue';
import {getWebtoonsByPlatform} from "../../../service/PlatformService.js";

const props = defineProps({
  platform: String
})

const webtoons = ref([]);

watch(() => props.platform,async (requestPlatform) => {
  try {
    webtoons.value = await getWebtoonsByPlatform(requestPlatform);
  } catch (error){
    console.error('Failed to fetch webtoons:', error);
  }
},{immediate: true})


</script>

<style scoped>
.webtoon-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
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
  width: 100%;
  height: auto;
  object-fit: cover;
}

.webtoon-title{
  white-space: nowrap;
  max-width: 100%;
}


/* 화면 크기에 따라 열의 개수를 조정합니다 */
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