<template>
  <div class="login-container">
    <Card>
      <template #title>
        <Button class="title-btn" label="WebDuck" severity="contrast" outlined size="large" />
      </template>
      <template #content>
        <div class="button-container">
          <a :href="googleLoginUrl" class="btn-link">
            <img :src="google" class="login-icon" alt="google_login">
          </a>
          <a :href="naverLoginUrl" class="btn-link">
          <img :src="naver" class="login-icon" alt="naver_login">
          </a>
          <a :href="kakaoLoginUrl"class="btn-link">
          <img :src="kakao" class="login-icon" alt="kakao_login">
          </a>
        </div>
      </template>
    </Card>
  </div>
</template>
<script setup>
import google from '../../assets/login/btn_google.svg';
import naver from '../../assets/login/btn_naver.svg';
import kakao from '../../assets/login/btn_kakao.svg';
import { computed } from 'vue';


// TODO env 로 변경
const backendUrl = "http://localhost:8080"
const googleLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/google`);
const naverLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/naver`);
const kakaoLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/kakao`);



</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 75vh;
}

.title-btn{
  display: block;
  margin: 0 auto;
  font-size: 1.5rem;
  font-weight: bold;
}

.btn-link{
  display: inline-block;
  margin: 0 10px;
}

.login-icon {
  width: 50px;
  height: 50px;
  margin: 0 0.5rem;
}


</style>
