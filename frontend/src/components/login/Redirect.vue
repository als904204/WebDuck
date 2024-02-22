<template>
  <div>
  </div>
</template>


<script setup>
import {router} from "../../router/index.js";
import {onMounted} from "vue";
import {checkLoginStatus} from "../../store/auth.js";


onMounted(async () => {
  const loginSuccess = await checkLoginStatus();
  if (loginSuccess) {
    await router.push({path: '/'})
  } else {
    alert('로그인 실패');
    await router.push({path: '/login'})
  }
});

</script>