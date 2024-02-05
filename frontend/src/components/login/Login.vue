<template>
  <div class="login-container">
    <Card>
      <template #title>
        <Button class="title-btn" label="WebDuck" severity="contrast" outlined size="large" />
      </template>
      <template #content>
        <div class="button-container">
          <a href="" class="btn-link">
            <img :src="google" class="login-icon" alt="google_login">
          </a>
          <a href="" class="btn-link">
          <img :src="naver" class="login-icon" alt="naver_login">
          </a>
          <a href="" class="btn-link">
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
