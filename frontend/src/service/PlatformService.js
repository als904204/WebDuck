import axios from 'axios';

export const getWebtoonsByPlatform = async (platform) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/webtoon/platform?type=${platform}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for platform', platform, error);
    throw error;
  }
};
