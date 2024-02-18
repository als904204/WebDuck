import axios from "axios";
import {useRouter} from "vue-router";

export const fetchWebtoonDetails =  async (id) => {
  const router = useRouter();
  try {
    const response = await axios.get(`/api/v1/webtoon/${id}`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      await router.push('/notFound'); // 404 에러가 발생했을 때 NotFound 페이지로 리다이렉트
    }
    throw error;
  }
}

export const fetchWebtoonRatingAvg =  async (id) => {
  try {
    const response = await axios.get(`/api/v1/review/${id}/avg`);
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const fetchWebtoonCount =  async (id) => {
  try {
    const response = await axios.get(`/api/v1/review/${id}/count`);
    return response.data;
  } catch (error) {
    throw error;
  }
}


