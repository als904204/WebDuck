<template>
  <div class="publish-day-webtoons">
    <div>
      <Badge class="publish-btn" value="월요일 웹툰" size="xlarge" severity="success"></Badge>
      <FetchWebtoonList param="MONDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="화요일 웹툰" size="xlarge" severity="info"></Badge>
      <FetchWebtoonList param="TUESDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="수요일 웹툰" size="xlarge" severity="warning"></Badge>
      <FetchWebtoonList param="WEDNESDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="목요일 웹툰" size="xlarge" severity="danger"></Badge>
      <FetchWebtoonList param="THURSDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="금요일 웹툰" size="xlarge" severity="primary"></Badge>
      <FetchWebtoonList param="FRIDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="토요일 웹툰" size="xlarge" severity="info"></Badge>
      <FetchWebtoonList param="SATURDAY" serviceType="PUBLISH" />
    </div>
    <div>
      <Badge class="publish-btn" value="일요일 웹툰" size="xlarge" severity="warning"></Badge>
      <FetchWebtoonList param="SUNDAY" serviceType="PUBLISH" />
    </div>
  </div>
</template>

<script setup>
import FetchWebtoonList from "./list/WebtoonList.vue";
</script>

<style scoped>
.publish-btn{
  margin-left:1.3%;
  margin-bottom:8px;
}
</style>
