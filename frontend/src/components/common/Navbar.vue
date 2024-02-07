<template>
  <Menubar :model="items" class="menubar"></Menubar>
</template>

<script setup>
import {computed} from "vue";
import { useRouter } from 'vue-router';
import {isLoggedIn, logout} from "../../store/auth.js";

const router = useRouter();

const handleLogout = async () => {
  await logout();
  await router.push("/");
}

// 반응형으로 메뉴 항목을 설정
const items = computed(() => {
  // 기본 메뉴 항목
  const baseItems = [
    { label: '홈', icon: 'pi pi-home', command: () => router.push("/") },
    { label: '장르별 웹툰', icon: 'pi pi-book', command: () => router.push("/genre") },
    { label: '요일별 웹툰', icon: 'pi pi-book', command: () => router.push("/publish") },
    { label: '카카오 웹툰', icon: 'pi pi-book', command: () => router.push("/kakao") },
    { label: '네이버 웹툰', icon: 'pi pi-book', command: () => router.push("/naver") },
  ];

  // 로그인 상태에 따라 '로그인/가입' 또는 '내정보' 메뉴 항목 추가
  if (isLoggedIn.value) {
    baseItems.push({ label: '내정보', icon: 'pi pi-user', command: () => router.push("/profile") });
    baseItems.push({ label: '로그아웃', icon: 'pi pi-user', command: handleLogout });
  } else {
    baseItems.push({ label: '로그인/가입', icon: 'pi pi-user', command: () => router.push("/login") });
  }
  return baseItems;
});
</script>


<style scoped>
.menubar {
  margin-bottom : 40px;
}

.p-menubar {
  display: flex;
  justify-content: center;
}
.p-menubar-root-list{
  gap: 23px;
}
</style>