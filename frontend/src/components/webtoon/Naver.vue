<template>
<h1>NAVER 웹툰</h1>

  <div>
    <FetchWebtoonList param="NAVER" serviceType="PLATFORM" />
  </div>
</template>

<script setup>
import FetchWebtoonList from "./list/WebtoonList.vue";
</script>
