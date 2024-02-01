<template>
  <div class="banner-container">
    <Carousel :autoplay="2500" :wrap-around="true">
      <Slide v-for="(img, index) in images" :key="index">
        <div class="carousel__item">
          <img :src="img" :alt="`Slide ${index}`" class="carousel__image" />
        </div>
      </Slide>
      <template #addons>
        <Pagination />
      </template>
    </Carousel>
  </div>


  <!-- 인기 웹툰 세션 -->
  <section>
    <div class="webtoon-container">
      <h2 class="title-webtoon">🔥이번주 인기 웹툰</h2>
      <div class="btn-container">
        <Button label="리뷰순" @click="setPopularCriteria('COUNT');" severity="Primary" size="large" rounded />
        <Button label="평점순" @click="setPopularCriteria('RATING');" severity="Primary" size="large" rounded />
      </div>
    </div>
    <FetchWebtoonList :param="popularCriteria.param" :serviceType="popularCriteria.serviceType" />
  </section>



  <section>
    <div class="webtoon-container">
      <h2 class="title-webtoon">📆요일별 웹툰</h2>
      <div class="btn-container">
        <Button label="월"  @click="setPublishCriteria('MONDAY');" severity="Primary" size="large" rounded />
        <Button label="화"  @click="setPublishCriteria('TUESDAY');" severity="Primary" size="large"  rounded />
        <Button label="수"  @click="setPublishCriteria('WEDNESDAY');" severity="Primary" size="large"  rounded />
        <Button label="목"  @click="setPublishCriteria('THURSDAY');" severity="Primary" size="large"  rounded />
        <Button label="금"  @click="setPublishCriteria('FRIDAY');" severity="Primary" size="large"  rounded />
        <Button label="토"  @click="setPublishCriteria('SATURDAY');" severity="Primary" size="large"  rounded />
        <Button label="일"  @click="setPublishCriteria('SUNDAY', 'PUBLISH');" severity="Primary" size="large"  rounded />
      </div>
      <FetchWebtoonList :param="publishCriteria.param" :serviceType="publishCriteria.serviceType" />
    </div>
  </section>


  <section>
    <div class="webtoon-container">
      <h2 class="title-webtoon">✍🏻플랫폼별 웹툰</h2>
      <div class="btn-container">
        <Button label="카카오" @click="setPlatformCriteria('KAKAO')" severity="Primary" size="large" rounded />
        <Button label="네이버" @click="setPlatformCriteria('NAVER')"severity="Primary" size="large" rounded />
        <Button label="그외" severity="Primary" size="large" rounded />
      </div>
    </div>
    <FetchWebtoonList :param="platformCriteria.param" :serviceType="platformCriteria.serviceType" />
  </section>

</template>

<script setup>
import { ref, reactive } from 'vue';
import FetchWebtoonList from "../webtoon/list/WebtoonList.vue";
import { Carousel, Slide, Pagination } from 'vue3-carousel';
import 'vue3-carousel/dist/carousel.css';
import notice1 from '../../assets/notice1.png';
import notice2 from '../../assets/notice2.png';

const images = ref([
  notice1,
  notice2
]);

// 인기 웹툰, 요일별 웹툰, 플랫폼별 웹툰에 대한 상태를 별도로 관리
const popularCriteria = reactive({ serviceType: 'POPULAR', param: 'COUNT' });
const publishCriteria = reactive({ serviceType: 'PUBLISH', param: 'MONDAY' });
const platformCriteria = reactive({ serviceType: 'PLATFORM', param: 'KAKAO' });

// 각 세션별 웹툰 기준을 설정하는 함수
function setPopularCriteria(param) {
  popularCriteria.param = param;
  popularCriteria.serviceType = 'POPULAR';
}

function setPublishCriteria(param) {
  publishCriteria.param = param;
  publishCriteria.serviceType = 'PUBLISH';
}

function setPlatformCriteria(param) {
  platformCriteria.param = param;
  platformCriteria.serviceType = 'PLATFORM';
}

</script>


<style scoped>

.banner-container{
  margin-bottom:52px;
}
.carousel__image {
  width: 100%;
  height:auto;
  border-radius: 8px;
}

.title-webtoon {
  color: rgb(0, 0, 0);
  font-size: 30px;
  font-style: normal;
  font-weight: 700;
  line-height: 100%;
  letter-spacing: -0.78px;
  margin: 0px 0px 24px;
}


.btn-container{
  display:flex;
  gap:1rem 10px;
  margin-bottom : 24px;
}

.webtoon-container {
  margin-bottom : 80px;
}
</style>