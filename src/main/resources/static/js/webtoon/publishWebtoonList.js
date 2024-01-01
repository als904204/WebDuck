document.addEventListener('DOMContentLoaded', function() {
  // 첫 페이지 로딩 시 월요일 웹툰 목록을 불러옵니다.
  loadWebtoonList('MONDAY');
  setActiveDayButton('MONDAY');
});

// 요일별 버튼 클릭 이벤트 설정
document.querySelectorAll('[data-publish-day]').forEach(button => {
  button.addEventListener('click', function() {
    const publishDay = this.getAttribute('data-publish-day');
    loadWebtoonList(publishDay);
    setActiveDayButton(publishDay);
  });
});

// 웹툰 목록을 불러오는 함수
function loadWebtoonList(publishDay) {
  fetch(`/api/v1/webtoon/publish?publishDay=${publishDay}`)
  .then(response => response.json())
  .then(webtoonList => updateWebtoonList(webtoonList))
  .catch(error => console.error('Error:', error));
}

// 웹툰 목록을 업데이트하는 함수
function updateWebtoonList(webtoonList) {
  const container = document.getElementById('webtoonListContainer');
  container.innerHTML = ''; // 목록 초기화

  webtoonList.forEach(webtoon => {
    const col = document.createElement('div');
    col.className = 'col-6 col-md-2';
    col.innerHTML = `
      <img class="img-fluid equal-height-img" src="${webtoon.imagePath}" alt="${webtoon.title}" />
      <p class="text-center">${webtoon.title}</p>
    `;
    container.appendChild(col);
  });
}

// 활성화된 요일 버튼을 설정하는 함수
function setActiveDayButton(publishDay) {
  document.querySelectorAll('[data-publish-day]').forEach(button => {
    button.classList.remove('active'); // 다른 버튼의 활성 상태 제거
  });
  document.querySelector(`[data-publish-day=${publishDay}]`).classList.add('active'); // 선택된 요일 버튼 활성화
}
