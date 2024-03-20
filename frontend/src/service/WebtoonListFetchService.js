import axios from 'axios';

// 인기별 웹툰 요청 평점순,리뷰순 (RATING,COUNT)
export const getWebtoonsByPopular = async (condition) => {
  try {
    const response = await axios.get(`/api/v1/webtoon/popular?condition=${condition}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for popular', condition, error);
    throw error;
  }
};

// 요일별 웹툰 요청 (월요일, 화요일...일요일)
export const getWebtoonsByPublish = async (day) => {
  try {
    const response = await axios.get(`/api/v1/webtoon/publish?day=${day}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for day', day, error);
    throw error;
  }
};

// 플랫폼 별 웹툰 요청 (네이버,카카오,그 외)
export const getWebtoonsByPlatform = async (platform) => {
  try {
    const response = await axios.get(`/api/v1/webtoon/platform?type=${platform}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching webtoons for platform', platform, error);
    throw error;
  }
};

// 장르에 해당하는 웹툰 요청
export const getWebtoonsByGenres = async (genres) => {
  try {
    // 여러 장르 이름을 쿼리 스트링으로 변환
    const queryString = genres.map(genre => `names=${genre}`).join('&');
    const response = await axios.get(`/api/v1/webtoon/genres?${queryString}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

// 장르 목록 가져오기
export const getGenres = async () => {
  try {
    const response = await axios.get('/api/v1/genre');
    return response.data;
  } catch (error) {
    console.error('Error fetching genres', error);
    throw error;
  }
};

// 컬렉션 ID로 웹툰 목록 가져오기
export const fetchWebtoonsByCollectionId = async (id) => {
  try {
    const response = await axios.get(`/api/v1/webtoon/${id}/collection`);
    return response.data;
  } catch (err) {
    throw err;
  }
};