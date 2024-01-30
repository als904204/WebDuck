import { createRouter, createWebHistory } from 'vue-router'

import Home from "../components/Home.vue";
import Genre from "../components/webtoon/Genre.vue";
import Publish from "../components/webtoon/publish.vue";
import Kakao from "../components/webtoon/Kakao.vue";
import Naver from "../components/webtoon/Naver.vue";
import Login from "../components/login/Login.vue";

const routes = [
  {path : '/',component:Home},
  {path : '/genre', component: Genre},
  {path : '/publish', component: Publish},
  {path : '/kakao', component: Kakao},
  {path : '/naver', component: Naver},
  {path : '/login', component: Login},
]


// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 라우터 추출 (main.js에서 import)
export {router}