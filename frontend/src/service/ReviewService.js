import axios from "axios";

export const fetchReviewsByWebtoonId = async (id, page = 0, size = 5, nextId = null) => {
  try {
    const params = new URLSearchParams({
      page,
      size,
      ...(nextId && {nextId}),
    }).toString();
    const response = await axios.get(`/api/v1/review/${id}?${params}`);
    return response.data;
  } catch (error){
    throw error;
  }
}

export const submitReview = async (webtoonId,content,rating) => {
  try {
    const response = await axios.post(`/api/v1/review`, {
      webtoonId,
      content,
      rating,
    });

    return response.data;
  } catch (error){
    console.error("Review submission failed:",error)
    throw error;
  }
}