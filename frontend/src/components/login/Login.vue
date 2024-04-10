<template>
  <div class="login-container">
    <Card>
      <template #title>
        <Button class="title-btn" label="LOGIN" severity="contrast" outlined size="large" />
      </template>
      <template #content>

      <div class="grid">
          <div class="col">
            <div class="text-center p-3 border-round-sm bg-white font-bold">
              <a :href="googleLoginUrl" class="btn-link">
                <img :src="google" class="login-icon" alt="google_login">
              </a>
            </div>
          </div>

          <div class="col">
            <div class="text-center p-3 border-round-sm bg-white font-bold ">
              <a :href="kakaoLoginUrl"class="btn-link">
                <img :src="kakao" class="login-icon" alt="kakao_login">
              </a>
            </div>
          </div>
        </div>


        <div v-if="isDev"  class="col">
          <div class="text-center p-3 border-round-sm bg-white font-bold ">
              <Button label="관리자 로그인" @click="adminLogin"></Button>
          </div>
          <small class="text-red-500">관리자 로그인은 개발 환경에서만 표시 되어야 합니다</small>
        </div>

      </template>
    </Card>
  </div>
</template>


<script setup>
import google from '../../assets/login/btn_google.svg';
import kakao from '../../assets/login/btn_kakao.svg';
import { computed } from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";

const backendUrl = import.meta.env.VITE_APP_BACKEND_URL;
const oauthRedirectUri = '/oauth2/redirect';

const redirectUri = backendUrl + oauthRedirectUri;


const googleLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/google?redirect_uri=${redirectUri}`);
const kakaoLoginUrl = computed(() => `${backendUrl}/oauth2/authorization/kakao?redirect_uri=${redirectUri}`);

const isDev = computed(() => import.meta.env.MODE === 'development');


let router;
let adminLogin;

if (isDev.value) {
  router = useRouter();
  adminLogin = async () => {
    try {
      await axios.get('/api/v1/dev/login');
      alert('관리자로 접속합니다.');
      await router.push('/oauth2/redirect');
    } catch (error) {
      alert('관리자 로그인 실패');
    }
  };
} else {
  adminLogin = () => {};
}
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
