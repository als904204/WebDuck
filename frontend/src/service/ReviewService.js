import axios from "axios";

// 리뷰 목록
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

// 리뷰 저장
export const submitReview = async (webtoonId,content,rating) => {
  try {
    const response = await axios.post(`/api/v1/review`, {
      webtoonId,
      content,
      rating,
    });

    return response.data;
  } catch (error){
    throw error;
  }
}

// 리뷰 삭제
export const removeReview = async (reviewId) => {
  try {
    const response = await axios.delete(`/api/v1/review/${reviewId}`);
    alert('성공')
  } catch (error) {
    // 에러 처리
    throw error;
  }
};

export const updateReviewLikes = async (reviewId) => {
  try{
    const response = await axios.patch(`/api/v1/review/${reviewId}/likes`, {
      reviewId,
    });

    return response.data;
  }catch(error){
    throw error;
  }
};