document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('btnReviewCount').addEventListener('click', function() {
    loadWebtoons('COUNT');
  });

  document.getElementById('btnRatingAvg').addEventListener('click', function() {
    loadWebtoons('RATING');
  });

  // 초기 로딩 시 기본 정렬 방식으로 목록 로드
  loadWebtoons('RATING');
});

function loadWebtoons(sortBy) {
  fetch(`/api/v1/webtoon/popular?sortBy=${sortBy}`)
  .then(response => response.json())
  .then(webtoons => updateWebtoonList(webtoons))
  .catch(error => console.error('Error:', error));
}

function updateWebtoonList(webtoons) {
  const container = document.getElementById('popularWebtoonList');
  container.innerHTML = ''; // 목록 초기화

  webtoons.forEach(webtoon => {
    const col = document.createElement('div');
    col.className = 'col-6 col-md-2';
    col.innerHTML = `
      <img class="img-fluid equal-height-img" src="${webtoon.imagePath}" alt="${webtoon.title}" />
      <p class="text-center">${webtoon.title}</p>
    `;
    container.appendChild(col);
  });
}
