<template>
    <Navbar/>
    <router-view></router-view>
    <Footer/>
</template>

<script setup>
import Navbar from "./components/common/Navbar.vue";
import Footer from "./components/common/Footer.vue";
import {checkLoginStatus, isLoggedIn} from "./store/auth.js";
import {onMounted} from "vue";

onMounted(async () => {
  isLoggedIn.value = sessionStorage.getItem('isLoggedIn') === 'true';
  // 새로고침 CSRF 토큰을 다시 요청
  if (isLoggedIn.value) {
    try {
      await checkLoginStatus();
    } catch (error) {
      console.error("Error fetching CSRF token on page load", error);
    }
  }
});
</script>


<style scoped>


</style>
