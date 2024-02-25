import axios from 'axios';
import {ref} from "vue";

export const isLoggedIn = ref(false);
export const csrfToken = ref('');

export async function checkLoginStatus() {
  try {
    const response = await axios.get('/api/v1/auth/status');
    if(response.data === true){
      isLoggedIn.value = response.data;
      sessionStorage.setItem('isLoggedIn', 'true');
      await fetchCsrfToken(); // 로그인 상태일 때만 CSRF 토큰 요청
    }else{
      sessionStorage.removeItem('isLoggedIn');
      sessionStorage.removeItem('csrfToken');
    }
    return response.data;
  } catch (error) {
    console.error("Error checking login status", error);
    return false;
  }
}


async function fetchCsrfToken() {
  try {
    const response = await axios.get('/api/v1/auth/csrf');
    csrfToken.value = response.data;
    sessionStorage.setItem('csrfToken', csrfToken.value);
    axios.defaults.headers.common['X-CSRF-TOKEN'] = csrfToken.value;
  } catch (error) {
    console.error("Error fetching CSRF token", error);
    throw error;
  }
}



export async function logout() {
  const csrfToken = sessionStorage.getItem('csrfToken'); // 세션 스토리지에서 CSRF 토큰 가져오기
  try {
      await axios.post('/api/v1/auth/logout', {}, {
        headers: {
          'X-CSRF-TOKEN': csrfToken,
        },
        withCredentials: true
    });

    isLoggedIn.value = false;
    sessionStorage.setItem('isLoggedIn', 'false');
    sessionStorage.clear();
  } catch (error) {
    console.error("Error during logout", error);
    alert('알수없는 에러가 발생했습니다')
  }
}




