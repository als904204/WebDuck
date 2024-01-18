function createWebtoonElement(webtoon) {
  const webtoonElement = document.createElement('div');
  webtoonElement.className = 'col-6 col-md-2 mb-4';

  webtoonElement.innerHTML = `
    <a href="/webtoon/details/${webtoon.id}">
      <img class="img-fluid" src="${webtoon.imagePath}" alt="${webtoon.title}" style="height: 225px; object-fit: cover;">
    </a>
    <p class="text-center">${webtoon.title}</p>
  `;

  return webtoonElement;
}

// 장르별,카카오 웹툰
function updateGenreAndKakao(containerId, webtoons) {
  const container = document.querySelector(containerId);
  container.innerHTML = ''; // 기존 목록을 비웁니다.

  webtoons.forEach(webtoon => {
    const webtoonElement = createWebtoonElement(webtoon);
    container.appendChild(webtoonElement);
  });
}
