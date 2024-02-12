import { createRouter, createWebHistory } from 'vue-router'

import Home from "../components/home/Home.vue";
import Genre from "../components/webtoon/Genre.vue";
import Publish from "../components/webtoon/Publish.vue";
import Kakao from "../components/webtoon/Kakao.vue";
import Naver from "../components/webtoon/Naver.vue";
import Login from "../components/login/Login.vue";
import Review from "../components/webtoon/Review.vue";
import Profile from "../components/login/Profile.vue";

const routes = [
  {path : '/',component:Home},
  {path : '/genre', component: Genre},
  {path : '/publish', component: Publish},
  {path : '/kakao', component: Kakao},
  {path : '/naver', component: Naver},
  {path : '/login', component: Login},
  {path : '/profile', component: Profile},
  {path : '/review/:id',component: Review, name: 'review'}

]


// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 라우터 추출 (main.js에서 import)
export {router}