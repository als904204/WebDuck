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
  const naverWebtoonListContainer = document.querySelector('#naverWebtoonListContainer');
  naverWebtoonListContainer.innerHTML = ''; // 기존 목록을 비웁니다.

  webtoons.forEach(webtoon => {
    // 각 웹툰에 대한 HTML 요소를 생성합니다.
    const webtoonElement = document.createElement('div');
    webtoonElement.className = 'col-md-4'; // Bootstrap 그리드 클래스 적용

    // 웹툰 정보를 표시하는 HTML 구조를 생성합니다.
    webtoonElement.innerHTML = `
      <div class="card mb-4 shadow-sm">
        <img src="${webtoon.imagePath}" class="bd-placeholder-img card-img-top" width="100%" height="225" alt="${webtoon.title}">
        <div class="card-body">
          <p class="card-text">${webtoon.title}</p>
          <div class="d-flex justify-content-between align-items-center">
            <div class="btn-group">
              <button type="button" class="btn btn-sm btn-outline-secondary">리뷰 보기</button>
            </div>
          </div>
        </div>
      </div>
    `;

    // 생성된 요소를 웹툰 목록 컨테이너에 추가합니다.
    naverWebtoonListContainer.appendChild(webtoonElement);
  });
}
