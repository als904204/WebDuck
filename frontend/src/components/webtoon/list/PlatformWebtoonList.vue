<template>
  <div class="webtoon-grid">
    <div class="webtoon-item" v-for="webtoon in webtoons" :key="webtoon.id">
      <img class="webtoon-image" :src="webtoon.imagePath" :alt="webtoon.title">
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

.webtoon-image{
  width:100%;
  height: 100%;
}

.webtoon-title{
  white-space: nowrap;
  max-width: 100%;
}
</style>