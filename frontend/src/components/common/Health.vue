<template>
  <div>Version: {{ version }}</div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const version = ref("");

onMounted(async () => {
  try {
    version.value = await fetchVersion();
    console.log(version.value);
  } catch (error) {
    throw error;
  }
});

const fetchVersion = async () => {
  try {
    const response = await axios.get(`/api/v1/health`);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};
</script>