document.addEventListener('DOMContentLoaded', function() {
  // 첫 페이지 로딩 시 월요일 웹툰 목록을 불러옵니다.
  loadPlatformWebtoon('KAKAO');
  setActivePlatformButton('KAKAO');
});

// 요일별 버튼 클릭 이벤트 설정
document.querySelectorAll('[data-platform]').forEach(button => {
  button.addEventListener('click', function() {
    const platform = this.getAttribute('data-platform');
    loadPlatformWebtoon(platform);
    setActivePlatformButton(platform);
  });
});

// 웹툰 목록을 불러오는 함수
function loadPlatformWebtoon(platform) {
  fetch(`/api/v1/webtoon/platform?platform=${platform}`)
  .then(response => response.json())
  .then(platformWebtoons => updatePlatformWebtoonList(platformWebtoons))
  .catch(error => console.error('Error:', error));
}

// 웹툰 목록을 업데이트하는 함수
function updatePlatformWebtoonList(platformWebtoons) {
  const container = document.getElementById('platformWebtoonList');
  container.innerHTML = ''; // 목록 초기화

  platformWebtoons.forEach(webtoon => {
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
function setActivePlatformButton(platform) {
  document.querySelectorAll('[data-platform]').forEach(button => {
    button.classList.remove('active'); // 다른 버튼의 활성 상태 제거
  });
  document.querySelector(`[data-platform=${platform}]`).classList.add('active'); // 선택된 요일 버튼 활성화
}
