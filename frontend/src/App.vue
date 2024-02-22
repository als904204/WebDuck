<template>
    <Navbar/>
    <router-view></router-view>
    <Footer/>
</template>

<script setup>
import Navbar from "./components/common/Navbar.vue";
import Footer from "./components/common/Footer.vue";
import {csrfToken, isLoggedIn} from "./store/auth.js";
import {onMounted} from "vue";

onMounted(() => {
  isLoggedIn.value = sessionStorage.getItem('isLoggedIn') === 'true';
});
</script>


<style scoped>


</style>
