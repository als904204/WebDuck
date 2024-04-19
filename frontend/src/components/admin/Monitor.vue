<template>
  <h1> JVM Memory Monitor </h1>
  <p>totalMemory : {{ totalMemory }}</p>
  <p>usedMemory : {{ usedMemory }}</p>
  <p>freeMemory : {{ freeMemory }}</p>
  <p>hostProcessors : {{ hostProcessors }}</p>

</template>

<script setup>
import {ref, onUnmounted, onMounted} from 'vue';


onMounted(() => {
  connectWebSocket();

});

const totalMemory = ref('');
const usedMemory = ref('');
const freeMemory = ref('');
const hostProcessors = ref();

let webSocket = null;

const connectWebSocket = () => {
  const baseUrl = import.meta.env.VITE_APP_BACKEND_URL;

  const socketUrl = baseUrl.replace(/^http/, 'ws') + '/api/v1/admin/memory';
  console.log(socketUrl);
  const socket = new WebSocket(socketUrl);

  socket.onopen = () => {
    console.log('WebSocket connection open')
  };

  socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    totalMemory.value = data.totalMemory;
    usedMemory.value = data.usedMemory;
    freeMemory.value = data.freeMemory;
    hostProcessors.value = data.hostProcessors;

  };

  socket.onclose = () => {
    console.log('WebSocket connection closed');
  }

  webSocket = socket;


  onUnmounted(() => {
    if (webSocket) {
      webSocket.close();
    }
  });
}

</script>
