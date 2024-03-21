<template>
<div>
  {{ version }}
</div>
</template>

<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";

const version = ref("");

onMounted(async () => {
  const versionInfo = await fetchVersion();
  version.value = versionInfo;
});

const fetchVersion = async () => {
  try {
    const response = await axios.get('/api/v1/health');
    version.value = response.data;
    return response.data.version;
  } catch (err) {
    alert("서버 장애");
    return "";
  }
}

</script>
