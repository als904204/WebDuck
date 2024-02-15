import axios from "axios";

export const fetchWebtoonDetails =  async (id) => {
  try {
    const response = await axios.get(`/api/v1/webtoon/${id}`);
    return response.data;
  } catch (error) {
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


