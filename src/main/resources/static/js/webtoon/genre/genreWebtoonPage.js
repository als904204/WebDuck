document.addEventListener('DOMContentLoaded', () => {
  fetchGenres();
});

function fetchGenres() {
  fetch('/api/v1/genre')
  .then(response => response.json())
  .then(genres => {
    const genreContainer = document.querySelector('.genre div:nth-child(2)');
    genreContainer.innerHTML = ''; // 기존 버튼 초기화

    genres.forEach((genre, index) => {
      const button = document.createElement('button');
      button.className = 'btn btn-secondary btn-circle btn-xl me-2 mb-2';
      button.dataset.genre = genre.genreName.toUpperCase();
      button.innerHTML = `<span>${translateGenre(genre.genreName)}</span>`;
      button.addEventListener('click', () => fetchWebtoonsByGenre(genre.genreName));

      genreContainer.appendChild(button);

      // 첫 화면에서 첫 번째 장르의 웹툰 목록을 가져옵니다.
      if (index === 0) {
        fetchWebtoonsByGenre(genre.genreName);
      }
    });
  })
  .catch(error => console.error('Error:', error));
}

function translateGenre(genreName) {
  const genreTranslations = {
    'MARTIALARTS': '무협',
    'FANTASY': '판타지',
    'ADULT': '성인',
    'ROMANCE': '로맨스',
    'GAG': '개그'
    // 필요한 추가 장르 번역을 여기에 추가합니다.
  };
  return genreTranslations[genreName] || genreName;
}

function fetchWebtoonsByGenre(genre) {
  fetch(`/api/v1/webtoon/genre?name=${genre}`)
  .then(response => response.json())
  .then(genreWebtoons => updateWebtoonGenreList(genreWebtoons))
  .catch(error => console.error('Error:', error));
}
