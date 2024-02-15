import axios from 'axios';
import {ref} from "vue";

export const isLoggedIn = ref(false);
export const csrfToken = ref('');

export async function checkLoginStatus() {
  try {
    const response = await axios.get('/api/v1/auth/status');
    if(response.data === true){
      isLoggedIn.value = response.data;
      await fetchCsrfToken(); // 로그인 상태일 때만 CSRF 토큰 요청
    }
  } catch (error) {
    console.error("Error checking login status", error);
    throw error;
  }
}


async function fetchCsrfToken() {
  try {
    const response = await axios.get('/api/v1/auth/csrf');
    csrfToken.value = response.data;
    axios.defaults.headers.common['X-CSRF-TOKEN'] = csrfToken.value;
  } catch (error) {
    console.error("Error fetching CSRF token", error);
    throw error;
  }
}

export async function logout() {
  try {
    await axios.post('/api/v1/auth/logout', {}, {
      withCredentials: true
    });
    isLoggedIn.value = false;
  } catch (error) {
    console.error("Error during logout", error);
    throw error;
  }
}


