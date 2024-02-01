<template>
  <div class="webtoon-grid">
    <div class="webtoon-item" v-for="webtoon in webtoons" :key="webtoon.id">
      <router-link :to="`/review/${webtoon.id}`">
        <img class="webtoon-image" :src="webtoon.imagePath" :alt="webtoon.title">
      </router-link>
        <p class="webtoon-title">{{ webtoon.title }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, watchEffect } from 'vue';
import {
  getWebtoonsByPlatform,
  getWebtoonsByPopular,
  getWebtoonsByPublish
} from "../../../service/WebtoonListFetchService.js";

const props = defineProps({
  param: String,
  serviceType: String
});

const webtoons = ref([]);


// props의 변화를 감지하여 데이터를 가져오는 로직
watchEffect(async () => {
  if (!props.param || !props.serviceType) return; // 초기 상태 확인

  try {
    let data;
    if (props.serviceType === 'PLATFORM') {
      data = await getWebtoonsByPlatform(props.param);
    } else if (props.serviceType === 'POPULAR') {
      data = await getWebtoonsByPopular(props.param);
    } else if (props.serviceType === 'PUBLISH') {
      data = await getWebtoonsByPublish(props.param);
    } else {
      webtoons.value = []; // 서비스 타입이 지정되지 않은 경우
      return;
    }
    webtoons.value = data;
  } catch (error) {
    console.error(`Error fetching webtoons for ${props.serviceType}`, props.param, error);
  }
});





// const props = defineProps({
//   platform: String
// })
// const webtoons = ref([]);
//
// watch(() => props.platform,async (requestPlatform) => {
//   try {
//     webtoons.value = await getWebtoonsByPlatform(requestPlatform);
//   } catch (error){
//     console.error('Failed to fetch webtoons:', error);
//   }
// },{immediate: true})

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