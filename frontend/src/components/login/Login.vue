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
import kakao from '../../assets/login/btn_kakao.svg';
import { computed } from 'vue';

const backendUrl = import.meta.env.VITE_APP_BACKEND_URL;
const oauthRedirectUri = '/oauth2/redirect';

const redirectUri = backendUrl + oauthRedirectUri;


const googleLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/google?redirect_uri=${redirectUri}`);
const kakaoLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/kakao?redirect_uri=${redirectUri}`);

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
