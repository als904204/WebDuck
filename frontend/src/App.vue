<template>
    <Navbar/>
    <router-view></router-view>
    <Footer/>
</template>

<script setup>
import Navbar from "./components/common/Navbar.vue";
import Footer from "./components/common/Footer.vue";
import { onMounted } from 'vue';
import {checkLoginStatus} from "./store/auth.js";

onMounted(async () => {
  await checkLoginStatus();
})

</script>


<style scoped>


</style>
