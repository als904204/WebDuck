<template>
  <div class="container flex-column">



    <div class="card flex mt-4">
      <p class="text-2xl mr-4">닉네임</p>
      <InputText v-model="userNickname" type="text" class="text-2xl mr-3" />
      <Button label="변경" size="large" @click="updateUserNickname"/>
    </div>
    <div class="card flex mt-4">
      <p class="text-2xl mr-4">최근 접속일 : <span>{{prevLoginAt}}</span></p>
    </div>
    <div class="card flex mt-4">
      <p class="text-2xl mr-4">작성한 리뷰 개수 : <span>{{reviewCount}}</span></p>
    </div>

    <div class="card flex mt-4">
      <p class="text-2xl mr-4">받은 좋아요 수 : <span>{{likesCount}}</span></p>
    </div>


    <div>
      <Message v-if="errorStatus === '409'"  severity="error" :closable="false">중복된 닉네임 입니다</Message>
      <Message v-if="errorStatus === '404'"  severity="error" :closable="false">세션 만료. 다시 로그인해 주세요</Message>
      <Message v-if="errorStatus === '400'"  severity="error" :closable="false">닉네임을 확인해주세요 (2자 이상 16자 이하)</Message>
      <Message v-if="errorStatus === '500'"  severity="error" :closable="false">알수 없는 에러 발생</Message>
      <Message v-if="errorStatus === '200'"  severity="info" :closable="false">닉네임 변경 성공</Message>
    </div>



  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue';
import {fetchUserInfo, updateNickname} from "../../service/UserService.js";
import Message from "primevue/message";
import {timeAgo} from "../../service/TimeAgoConverter.js";

let prevLoginAt = ref();
const reviewCount = ref();
const likesCount = ref();

const userNickname = ref(null);
let errorStatus = ref('');

onMounted(async () => {
  try {
    const userInfo = await fetchUserInfo();

    reviewCount.value = userInfo.reviewCount;
    likesCount.value = userInfo.likesCount;
    userNickname.value = userInfo.username;

    prevLoginAt = computed(() => timeAgo(userInfo.prevLoginAt));


  } catch (error) {
    throw error;
  }
});



const updateUserNickname = async () => {

  const updateUsername = userNickname.value.trim();

  if(!updateUsername || updateUsername.length < 2 || updateUsername.length > 16){
    errorStatus.value = '400';
    return;
  }

  try {
    await updateNickname(updateUsername);
    errorStatus.value = '200';
  } catch (error) {
    if (error.message === '409') {
      errorStatus.value = '409';
    } else if (error.message === '400') {
      errorStatus.value = '400';
    } else if (error.message === '404') {
      errorStatus.value = '404';
    } else {
      errorStatus.value = '500';
      console.error(error);
    }
  }
};


</script>

<style scoped>
.container {
  padding-right: 15px;
  padding-left: 15px;
  margin-right: auto;
  min-height: 64vh;
  margin-left: auto;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  @media (min-width: 576px) { max-width: 540px; }
  @media (min-width: 768px) { max-width: 720px; }
  @media (min-width: 992px) { max-width: 960px; }
  @media (min-width: 1200px) { max-width: 1140px; }
}

</style>