document.addEventListener('DOMContentLoaded', () => {
  fetchNaver();
});

function fetchNaver() {
  const naver = "NAVER";
  fetch(`/api/v1/webtoon/platform?platform=${naver}`)
  .then(response => response.json())
  .then(webtoons => {
    getNaverWebtoonList(webtoons); // 웹툰 목록 업데이트 함수 호출
  })
  .catch(error => console.error('Error : ',error))
}



function getNaverWebtoonList(webtoons) {
  const container = document.querySelector('#naverWebtoonListContainer');
  container.innerHTML = ''; // 기존 목록을 비웁니다.

  webtoons.forEach(webtoon => {
    // 웹툰 정보를 표시하는 HTML 요소를 생성합니다.
    const webtoonElement = document.createElement('div');
    webtoonElement.className = 'col-6 col-md-2 mb-4'; // Bootstrap 그리드 클래스 적용

    // 웹툰 이미지와 제목을 표시하는 HTML 구조를 생성합니다.
    webtoonElement.innerHTML = `
      <a href="/webtoon/details/${webtoon.id}">
        <img class="img-fluid" src="${webtoon.imagePath}" alt="${webtoon.title}" style="height: 225px; object-fit: cover;">
      </a>
      <p class="">${webtoon.title}</p>
    `;

    // 생성된 요소를 웹툰 목록 컨테이너에 추가합니다.
    container.appendChild(webtoonElement);
  });
}
