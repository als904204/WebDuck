import { createRouter, createWebHistory } from 'vue-router'

import Home from "../components/home/Home.vue";
import Genre from "../components/webtoon/Genre.vue";
import Publish from "../components/webtoon/Publish.vue";
import Kakao from "../components/webtoon/Kakao.vue";
import Naver from "../components/webtoon/Naver.vue";
import Login from "../components/login/Login.vue";
import Review from "../components/review/Review.vue";
import Profile from "../components/profile/Profile.vue";
import NotFound from "../components/common/NotFound.vue";
import Redirect from "../components/login/Redirect.vue";
import CollectionList from "../components/collection/Collections.vue";
import CollectionAdd from "../components/collection/CollectionCreate.vue";
import CollectionDetails from "../components/collection/CollectionDetails.vue";
import CollectionUpdate from "../components/collection/CollectionUpdate.vue";
import Health from "../components/common/Health.vue";

const routes = [
  {path : '/',component:Home},
  {path : '/genre', component: Genre},
  {path : '/publish', component: Publish},
  {path : '/kakao', component: Kakao},
  {path : '/naver', component: Naver},
  {path : '/login', component: Login},
  {path : '/profile', component: Profile},
  {path : '/collection', component: CollectionList, name: "collection"},
  {path : '/collection/add', component: CollectionAdd, name: 'collection/add'},
  {path: '/collection/update/:id', component: CollectionUpdate, name: 'collection.update'},
  {path : '/collection/:id',component: CollectionDetails, name: 'collectionDetails'},
  {path : '/review/:id',component: Review, name: 'review'},
  {path: '/health', component: Health},
  {path : '/notFound', component: NotFound, name: 'notFound'},
  {path: "/:pathMatch(.*)*", redirect: "/notFound"},
  {path: '/oauth2/redirect', component: Redirect}
]


// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 라우터 추출 (main.js에서 import)
export {router}