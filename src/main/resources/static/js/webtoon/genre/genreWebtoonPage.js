document.addEventListener('DOMContentLoaded', () => {
  fetchGenres();
});

let selectedGenres = new Set();


function fetchGenres() {
  fetch('/api/v1/genre')
  .then(response => response.json())
  .then(genres => {
    const genreButtonsContainer = document.querySelector('#genreButtons');
    genreButtonsContainer.innerHTML = '';

    genres.forEach((genre, index) => {
      const button = document.createElement('button');
      button.classList.add('btn', 'btn-genre', 'me-2', 'mb-2');
      button.dataset.genre = genre.genreName;
      button.textContent = translateGenre(genre.genreName);
      button.addEventListener('click', () => toggleGenreSelection(genre.genreName));

      genreButtonsContainer.appendChild(button);

      // 첫 번째 장르 버튼을 활성화합니다.
      if (index === 0) {
        button.click();
      }
    });
  })
  .catch(error => console.error('Error:', error));
}

function toggleGenreSelection(genreName) {
  const button = document.querySelector(`button[data-genre='${genreName}']`);
  if (selectedGenres.has(genreName)) {
    selectedGenres.delete(genreName);
    button.classList.remove('active');
  } else {
    selectedGenres.add(genreName);
    button.classList.add('active');
  }
  fetchWebtoonsBySelectedGenres(); // 해당 장르로 필터링된 웹툰 목록을 가져오는 함수
}




function fetchWebtoonsBySelectedGenres() {
  const genresArray = Array.from(selectedGenres);
  const queryParams = genresArray.map(genre => `names=${genre}`).join('&');
  const url = `/api/v1/webtoon/genres?${queryParams}`;

  fetch(url)
  .then(response => response.json())
  .then(webtoons => {
    getWebtoonList(webtoons); // 웹툰 목록 업데이트 함수 호출
  })
  .catch(error => console.error('Error:', error));
}

function translateGenre(genreName) {
  const genreTranslations = {
    'MARTIALARTS': '무협',
    'FANTASY': '판타지',
    'ADULT': '성인',
    'ROMANCE': '로맨스',
    'GAG': '개그',
    'ACTION': '액션',
    'DAILYLIFE': '일상',
    'THRILLER': '스릴러',
    'DRAMA': '드라마',
    'EMOTION': '감성',
    'SPORTS': '스포츠'
  };
  return genreTranslations[genreName] || genreName;
}

function getWebtoonList(webtoons) {
  const webtoonListContainer = document.querySelector('#webtoonList');
  webtoonListContainer.innerHTML = ''; // 기존 목록을 비웁니다.

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
    webtoonListContainer.appendChild(webtoonElement);
  });
}
