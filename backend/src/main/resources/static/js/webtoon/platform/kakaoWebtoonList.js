document.addEventListener('DOMContentLoaded', () => {
  fetchKakao();
});

function fetchKakao() {
  const kakao = "KAKAO";
  fetch(`/api/v1/webtoon/platform?platform=${kakao}`)
  .then(response => response.json())
  .then(webtoons => {
    getKakaoWebtoonList(webtoons); // 웹툰 목록 업데이트 함수 호출
  })
  .catch(error => console.error('Error : ',error))
}


function getKakaoWebtoonList(webtoons) {
  // common/WebtoonList
  updateGenreAndKakao('#kakaoWebtoonListContainer', webtoons);
}
