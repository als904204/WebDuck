import axios from "axios";

export const WebtoonSaveApi = async (platform) => {
  try {
    const response = await axios.post('/api/v1/admin/webtoon', { platform });
    return response.data;
  } catch (error) {
    console.error("Error while saving webtoon:", error);
    throw error;
  }
}

export const DuplicateWebtoons = async () => {
  try {
    const response = await axios.delete('/api/v1/admin/webtoon');
    return response.data;
  } catch (error){
    throw error;
  }
}