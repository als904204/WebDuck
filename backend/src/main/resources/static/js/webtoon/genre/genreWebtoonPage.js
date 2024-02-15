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

  // 장르가 선택되지 않았으면 함수를 종료합니다.
  if (genresArray.length === 0) {
    console.log("장르를 선택해주세요.");
    return;
  }

  const queryParams = genresArray.map(genre => `names=${genre}`).join('&');
  const url = `/api/v1/webtoon/genres?${queryParams}`;

  fetch(url)
  .then(response => {
    if (response.status === 404) {
      // 장르에 해당하는 웹툰 목록이 없을 경우
      displayNoWebtoonsFoundMessage();
      return null; // 추가 처리를 중단합니다.
    }
    if (!response.ok) {
      console.error('Unknown error:', response.statusText);
      return null; // 추가 처리를 중단합니다.
    }
    return response.json(); // 응답을 JSON으로 파싱합니다.
  })
  .then(webtoons => {
    if (webtoons) {
      getWebtoonList(webtoons); // 웹툰 목록 업데이트 함수 호출
    }
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
  // common/WebtoonList
  updateGenreAndKakao('#webtoonList', webtoons);
}

function displayNoWebtoonsFoundMessage() {
  const container = document.getElementById('webtoonList');
  container.innerHTML = `
    <div class="text-center">
      <i class="fa-solid fa-xmark fa-3x"></i> 
      <h1 class="display-6 mt-3">원하시는 결과가 없어요</h1>
      <p>장르를 다시 선택해주세요</p>
    </div>
  `;
}
