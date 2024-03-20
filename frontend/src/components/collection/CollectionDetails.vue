<template>
  <div class="p-4">

    <div class="grid justify-content-center mb-3 text-center">
      <div class="col-12 sm:col-6 md:col-4 lg:col-6">
        <Card>
          <template #title>{{ collection.title }}</template>
          <template #content>
            <p class="m-0 bg-gray-50">
              {{ collection.description }}
            </p>
          </template>
          <template #footer>
            <i class="pi pi-user"></i> {{ collection.ownerName }} 님의 보관함
          </template>
        </Card>
      </div>
    </div>


    <div class="grid text-center p-5">
      <div class="col-12 sm:col-6 md:col-4 lg:col-2" v-for="webtoon in webtoons" :key="webtoon.id">
        <a :href="webtoon.webtoonUrl" target="_blank">
          <div class="p-3">
            <img :src="webtoon.imagePath" class="w-full" :alt="webtoon.title">
            <Button outlined raised severity="secondary" >{{ webtoon.title }} <i class="pi pi-external-link pl-2"></i></Button>
          </div>
        </a>
      </div>
    </div>


  </div>
</template>

<script setup>
import {useRoute} from "vue-router";
import {fetchCollectionById} from "../../service/CollectionService.js";
import {onMounted, ref} from "vue";
import {fetchWebtoonsByCollectionId} from "../../service/WebtoonListFetchService.js";

const route = useRoute()
const collectionId = route.params.id
const collection = ref([]);
const webtoons = ref([]);

onMounted(async () => {
  collection.value = await fetchCollectionById(collectionId);
  webtoons.value = await fetchWebtoonsByCollectionId(collectionId);
});



</script>