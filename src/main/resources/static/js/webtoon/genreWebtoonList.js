document.addEventListener('DOMContentLoaded', function() {
  // 첫 페이지 로딩 시 장르 웹툰 목록 불러옴
  loadGenreWebtoon('MARTIALARTS');
  setActiveGenreButton('MARTIALARTS');
});


// 장르별 버튼 클릭 이벤트 설정
document.querySelectorAll('[data-genre]').forEach(button => {
  button.addEventListener('click', function() {
    const genre = this.getAttribute('data-genre');
    loadGenreWebtoon(genre);
    setActiveGenreButton(genre);
  });
});


// 웹툰 목록을 불러오는 함수
function loadGenreWebtoon(genre) {
  fetch(`/api/v1/webtoon/genre?name=${genre}`)
  .then(response => response.json())
  .then(genreWebtoons => updateWebtoonGenreList(genreWebtoons))
  .catch(error => console.error('Error:', error));
}


// 웹툰 목록을 업데이트하는 함수
function updateWebtoonGenreList(genreWebtoons) {
  const container = document.getElementById('genreWebtoonList');
  container.innerHTML = ''; // 목록 초기화

  genreWebtoons.forEach(webtoon => {
    const col = document.createElement('div');
    col.className = 'col-6 col-md-2';
    col.innerHTML = `
      <img class="img-fluid equal-height-img" src="${webtoon.imagePath}" alt="${webtoon.title}" />
      <p class="text-center">${webtoon.title}</p>
    `;
    container.appendChild(col);
  });
}

// 활성화된 장르 버튼을 설정하는 함수
function setActiveGenreButton(genre) {
  document.querySelectorAll('[data-genre]').forEach(button => {
    button.classList.remove('active'); // 다른 버튼의 활성 상태 제거
  });
  document.querySelector(`[data-genre=${genre}]`).classList.add('active'); // 선택된 장 버튼 활성화
}
