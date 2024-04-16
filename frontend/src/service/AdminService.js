import axios from "axios";

export const WebtoonSaveApi = async (platform) => {
  try {
    const response = await axios.post('/api/v1/admin/webtoon', {platform});
    return response.data;
  } catch (error) {
    if (error.response.status === 416) {
      alert('요청한 페이지가 범위를 초과했습니다. 마지막 페이지까지 요청을 완료했습니다.');
    }else if (error.response.status === 400) {
      alert('해당 API서버 일일 트래픽을 초과하였습니다. 내일 시도해 주세요.');
    }else{
      alert('웹툰 데이터 요청 중 알 수 없는 오류가 발생했습니다.');
      console.log(error);
      throw error;
    }
  }
}
  export const DuplicateWebtoons = async () => {
    try {
      const response = await axios.delete('/api/v1/admin/webtoon');
      return response.data;
    } catch (error) {
      throw error;
    }
  }