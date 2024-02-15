<template>
  <h1>KAKAO 웹툰</h1>
  <div>
    <FetchWebtoonList param="KAKAO" serviceType="PLATFORM" />
  </div>
</template>

<script setup>
import FetchWebtoonList from "./list/WebtoonList.vue";
</script>
