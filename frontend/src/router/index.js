import { createRouter, createWebHistory } from 'vue-router'

import Home from "../components/Home.vue";

const routes = [
  {path : '/',component:Home}
]


// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 라우터 추출 (main.js에서 import)
export {router}