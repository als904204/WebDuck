import axios from 'axios';

export const getWebtoonsByPopular = async (condition) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/webtoon/popular?sortBy=${condition}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for popular', condition, error);
    throw error;
  }
};

export const getWebtoonsByPublish = async (day) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/webtoon/publish?day=${day}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for day', day, error);
    throw error;
  }
};

export const getWebtoonsByPlatform = async (platform) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/webtoon/platform?type=${platform}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for platform', platform, error);
    throw error;
  }
};
