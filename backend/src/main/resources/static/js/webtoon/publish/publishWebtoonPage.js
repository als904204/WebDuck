function loadWebtoonsForDay(englishDay) {
  fetch(`/api/v1/webtoon/publish?publishDay=${englishDay}`)
  .then(response => response.json())
  .then(webtoonList => updatePopularWebtoonList(webtoonList, englishDay))
  .catch(error => console.error('Error:', error));
}

function updatePopularWebtoonList(webtoonList, englishDay) {
  const container = document.getElementById(englishDay);

  const koreanDays = {
    'MONDAY': '월요일',
    'TUESDAY': '화요일',
    'WEDNESDAY': '수요일',
    'THURSDAY': '목요일',
    'FRIDAY': '금요일',
    'SATURDAY': '토요일',
    'SUNDAY': '일요일'
  };
  const koreanDayName = koreanDays[englishDay];

  container.innerHTML = `<h2 class="text-center">${koreanDayName}</h2>`; // 한글 요일 이름으로 표시

  webtoonList.forEach(webtoon => {
    const webtoonItem = document.createElement('div');
    webtoonItem.className = 'webtoon-item my-2';

    // a 태그를 생성하여 링크 추가
    const link = document.createElement('a');
    link.href = `/webtoon/details/${webtoon.id}`;

    const image = document.createElement('img');
    image.src = webtoon.imagePath;
    image.alt = webtoon.title;
    image.className = 'img-fluid';

    link.appendChild(image); // 이미지를 a 태그 안에 넣음
    webtoonItem.appendChild(link); // a 태그를 웹툰 아이템 안에 넣음

    const title = document.createElement('p');
    title.textContent = webtoon.title;
    webtoonItem.appendChild(title);

    container.appendChild(webtoonItem);
  });
}

// 페이지 로드 시 요일 데이터 로드
document.addEventListener('DOMContentLoaded', () => {
  const days = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
  days.forEach(day => loadWebtoonsForDay(day));
});
