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
import Admin from "../components/admin/Admin.vue";
import axios from "axios";
import {useAuthStore} from "../store/auth.js";


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
  {path : '/notFound', component: NotFound, name: 'notFound'},
  {path: "/:pathMatch(.*)*", redirect: "/notFound"},
  {path: '/oauth2/redirect', component: Redirect},
  {path : '/health', component: Health},
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    meta: {
      roles: ['ROLE_ADMIN']
    }

  }
]


// 라우터 생성
const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();
  if (!authStore.user) {
    await authStore.fetchUser();
  }

  // 권한 필요
  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    // 로그인하지 않은 사용자를 로그인 페이지로 리다이렉션
    next('/login');
  } else if (to.meta.roles && !to.meta.roles.includes(authStore.user?.role)) {
    // 필요한 권한이 없는 사용자를 notFound 페이지로 리다이렉션
    next('/notFound');
  } else {
    // 그 외의 경우 정상적으로 라우트 이동을 허용
    next();
  }

});


export {router};