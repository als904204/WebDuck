<template>
  <main class="container md:p-4">
    <div class="grid">
      <div class="image-container">
        <img class="border-round image" :src="webtoon.imagePath" :alt="webtoon.title"/>
      </div>
      <div class="md:p-6">
        <h2 class="text-5xl">{{ webtoon.title }}</h2>
        <p>
          <span>{{ webtoon.author }}</span> /
          <span>{{ webtoon.platform }}</span>
        </p>
        <p>{{webtoon.summary}}</p>
      </div>
    </div>

    <div class="my-3">
      <Card>
        <template #title>사용자 평</template>
        <template #content>
          <hr/>

          <div class="grid">
            <div class="col-12">

              <div class="flex justify-content-center">

                <div class="col-4 flex flex-column align-items-center">
                  <span class="text-2xl mb-1">나의 별점</span>
                  <span class="text-4xl mb-1">{{ userReviewRating }}</span>
                  <span class="mb-1">별점을 남겨주세요</span>
                  <Rating v-model="userReviewRating" :cancel="false" />
                </div>

                <div class="col-4 flex flex-column align-items-center">
                <span class="text-2xl mb-1">평균 별점</span>
                  <span class="text-4xl mb-1">{{avgRated}}</span>
                  <span class="mb-1">{{reviewCounted}}개의 리뷰</span>
                  <Rating v-model="avgRated" readonly :cancel="false" />
                </div>

                <div class="col-4 flex flex-column align-items-center">
                  <template v-if="avgStatus==='none'">
                    <span  class="text-2xl mb-2">아직 작성된 리뷰가 없어요!</span>
                    <i class="pi pi-times text-4xl mb-2" style="font-size: 1.5rem; color: grey;"></i>
                    <a :href="webtoon.webtoonUrl" target="_blank" class="mb-1 text-2xl"><Button label="보러가기" raised /></a>
                  </template>

                  <template v-else-if="avgStatus==='bad'">
                    <span  class="text-2xl mb-2">아쉬워요</span>
                    <i class="pi pi-thumbs-up-fill text-4xl mb-2" style="font-size: 1.5rem; color: #ff7f00;"></i>
                    <a :href="webtoon.webtoonUrl" target="_blank" class="mb-1 text-2xl"><Button label="보러가기" severity="Warning" raised /></a>
                  </template>

                  <template v-else-if="avgStatus==='good'">
                    <span  class="text-2xl mb-2">재밌어요</span>
                    <i class="pi pi-thumbs-up-fill text-4xl mb-2" style="font-size: 1.5rem; color: #008000;"></i>
                    <a :href="webtoon.webtoonUrl" target="_blank" class="mb-1 text-2xl"><Button label="보러가기" severity="success" raised /></a>
                  </template>

                  <template v-else>
                    <span class="text-2xl mb-2">엄청 재밌어요!</span>
                    <i class="pi pi-thumbs-up-fill text-4xl mb-2" style="font-size: 1.5rem; color: #0c70f2;"></i>
                    <a :href="webtoon.webtoonUrl" target="_blank" class="mb-1 text-2xl"><Button label="보러가기" severity="info" raised /></a>
                  </template>

                </div>
              </div>

              <div class="card flex justify-content-center mt-3">
                <Textarea v-model="userReviewContent" rows="3" cols="104" class="mr-2" placeholder="이 작품에 대한 평가를 남겨보세요!" />
                <Button label="등록" @click="createReview"/>
              </div>

              <div>
                <Message v-if="errorStatus === 'loginRequired'"  severity="error" :closable="false">로그인이 필요합니다</Message>
                <Message v-else-if="errorStatus === 'emptyContent'" severity="error" :closable="false">리뷰 내용을 입력해주세요</Message>
                <Message v-else-if="errorStatus === 'emptyRating'" severity="warn" :closable="false">리뷰 점수를 매겨주세요</Message>
                <Message v-else-if="errorStatus === 'success'"  :closable="false">리뷰 등록 성공!</Message>
                <Message v-else-if="errorStatus === 'unknownError'" :closable="false">알 수 없는 에러가 발생했습니다</Message>
              </div>


              <div>
                <p class="text-2xl">리뷰({{ reviewCounted}})</p>
              </div>

              <div v-for="review in reviews" :key="review.reviewId">
                <span>
                  <Rating v-model="review.rating" readonly :cancel="false" />
                  <p>
                    <span class="mr-4 font-bold">{{review.reviewerNickname}} </span>
                    <span class="text-gray-500">{{review.createdAt}}</span>
                  </p>
                  <p>{{review.content}}</p>
                    <Button :label="review.likesCount" @click="updateLikes(review.reviewId);" class="pi pi-thumbs-up mr-2"></Button>
                    <Button v-if="review.author"  @click="toggleTextarea"   class="pi pi-pencil mr-2" severity="info"></Button>
                  <hr/>
                </span>
              </div>
              <div v-if="hasNext" class="flex justify-content-center">
                  <Button label="더보기" @click="loadMoreReviews"></Button>
              </div>

              <div v-else class="flex justify-content-center">
                <p>마지막 리뷰입니다</p>
              </div>
            </div>
          </div>
        </template>
      </Card>
    </div>
  </main>

</template>

<script setup>
import {useRoute} from 'vue-router'
import {onMounted, ref} from "vue";
import {
  fetchWebtoonCount,
  fetchWebtoonDetails,
  fetchWebtoonRatingAvg
} from "../../service/WebtoonDetailsService.js";
import {
  fetchReviewsByWebtoonId,
  submitReview,
  updateReviewLikes
} from "../../service/ReviewService.js";
import {isLoggedIn} from "../../store/auth.js";
import Message from 'primevue/message';
import {timeAgo} from "../../service/TimeAgoConverter.js";

const route = useRoute()
const webtoonId = route.params.id
const webtoon = ref([]);
const reviews = ref([]);
const userReviewRating = ref(0);
const userReviewContent = ref();
const showReviewTextarea = ref(false)

const pageNum = ref(0);
const pageSize = ref(5);
const hasNext = ref(true);
const nextId = ref(null);


let avgRated = ref(null);
let errorStatus = ref('');
let avgStatus = ref('');
let reviewCounted = ref('');


// 웹툰 상세보기 정보
onMounted(async ()=>{
  try{
    webtoon.value = await fetchWebtoonDetails(webtoonId);

    await loadReviews();
  }catch(error){
    throw error
  }
});

// 웹툰 리뷰목록 가져오기
const loadReviews = async () => {
  try {
    if (hasNext.value) {
      const webtoonReviews = await fetchReviewsByWebtoonId(webtoonId, pageNum.value, pageSize.value,
          nextId.value);

      const reviewAvgResponse = await fetchWebtoonRatingAvg(webtoonId);
      avgRated.value = reviewAvgResponse.rating;

      const reviewCountResponse = await fetchWebtoonCount(webtoonId);
      reviewCounted.value = reviewCountResponse.count;

      if (avgRated.value === 0) {
        avgStatus = 'none';
      }else if (avgRated.value <= 2) {
        avgStatus = 'bad';
      } else if (avgRated.value <= 3) {
        avgStatus = 'good';
      } else if (avgRated.value > 3) {
        avgStatus = 'great';
      }




      const updatedReviews = webtoonReviews.item.map(review => ({
        ...review,
        createdAt: timeAgo(review.createdAt), // 날짜 변환 적용
      }));

      reviews.value.push(...updatedReviews);


      hasNext.value = webtoonReviews.hasNext;
      nextId.value = webtoonReviews.nextId;
      if(webtoonReviews.hasNext){
        pageNum.value +=1;
      }
    }

  } catch (error){
    console.error(error)
    throw error;
  }
}

// 리뷰 등록
const createReview = async () => {

  if(!isLoggedIn.value){
    errorStatus.value = 'loginRequired';
    return;
  }

  if (!userReviewContent.value || !userReviewContent.value.trim()) {
    errorStatus.value = 'emptyContent';
    return;
  }

  if(userReviewRating.value === 0){
    errorStatus.value = 'emptyRating';
    return;
  }

  try{
    await submitReview(webtoonId, userReviewContent.value, userReviewRating.value);
    errorStatus.value = 'success';
    userReviewContent.value = '';
    userReviewRating.value = 0;

    await refreshReviews();
  }catch (error){
    errorStatus.value = 'unknownError';
    userReviewContent.value = '';
    userReviewRating.value = 0;
  }
}




// 리뷰 좋아요
const updateLikes = async (reviewId) => {

  if(!isLoggedIn.value){
    errorStatus.value = 'loginRequired';
    return;
  }

  try{
    const response = await updateReviewLikes(reviewId);

    const reviewIndex = reviews.value.findIndex(r => r.reviewId === reviewId);

    if(reviewIndex !== -1){
      reviews.value[reviewIndex].likesCount = response.likesCount;
    }
  }catch (error){
    alert('알수 없는 에러가 발생했습니다.');
    return;
  }
};

// 리뷰 목록 새로고침
const refreshReviews = async () => {
  reviews.value = [];
  hasNext.value = true;
  nextId.value = null;
  pageNum.value = 0;
  await loadReviews();
};

// 리뷰 더보기 버튼
const loadMoreReviews = () => {
  loadReviews();
};

</script>

<style scoped>
.container {
  padding-right: 15px;
  padding-left: 15px;
  margin-right: auto;
  margin-left: auto;
  width: 100%;
  @media (min-width: 576px) { max-width: 540px; }
  @media (min-width: 768px) { max-width: 720px; }
  @media (min-width: 992px) { max-width: 960px; }
  @media (min-width: 1200px) { max-width: 1140px; }
}

.image-container {
  height: 360px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.image {
  max-height: 100%;
  max-width: 100%;
  object-fit: cover;
}


</style>
