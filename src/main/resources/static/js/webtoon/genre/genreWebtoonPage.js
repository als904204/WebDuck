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

// function getWebtoonList(webtoons) {
//   const container = document.querySelector('#webtoonList');
//   container.innerHTML = ''; // 기존 목록을 비웁니다.
//
//   webtoons.forEach(webtoon => {
//     const webtoonElement = document.createElement('div');
//     webtoonElement.className = 'col-6 col-md-2 mb-4'; // Bootstrap 그리드 클래스 적용
//
//     webtoonElement.innerHTML = `
//       <a href="/webtoon/details/${webtoon.id}">
//         <img class="img-fluid" src="${webtoon.imagePath}" alt="${webtoon.title}" style="height: 225px; object-fit: cover;">
//       </a>
//       <p>${webtoon.title}</p>
//     `;
//
//     container.appendChild(webtoonElement);
//   });
// }

function getWebtoonList(webtoons) {
  updateWebtoonList('#webtoonList', webtoons);
}
