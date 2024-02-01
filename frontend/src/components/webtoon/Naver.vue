<template>
<h1>NAVER</h1>
  <ul v-if="webtoons.length > 0">
    <li v-for="webtoon of webtoons" :key="webtoon.id">
      {{webtoon.title}}
    </li>
  </ul>
</template>

<script setup>
import {getWebtoonsByPlatform} from "../../service/WebtoonListFetchService.js";
import {onMounted, ref} from "vue";


const webtoons = ref([]);

onMounted(async () => {
  const data = await getWebtoonsByPlatform("NAVER");
  webtoons.value = data;
})

</script>
