// TODO : 리뷰 평균,개수 동적으로 뿌려
function fetchReviewAvg(webtoonId) {
  fetch(`/api/v1/review/${webtoonId}/avg`)
  .then(response => response.json())
  .then(avg => {
    const ratingAvg = document.getElementById('ratingAvg');
    const ratingStars = document.getElementById('ratingStars');
    if (ratingAvg) {
      const reviewAvgScore = avg.rating.toFixed(1);
      ratingStars.innerHTML = `회원리뷰 ${drawStars(reviewAvgScore)}`;
      ratingAvg.textContent = reviewAvgScore

    }
  })
  .catch(error => {
    console.error('평균 평점을 불러오는 중 에러 발생:', error);
  });
}

function fetchReviewCount(webtoonId) {
  fetch(`/api/v1/review/${webtoonId}/count`)
  .then(response => response.json())
  .then(count => {
    const reviewCount = document.getElementById('reviewCount');
    if (reviewCount) {
      reviewCount.textContent = `(${count.count}건)`; // 리뷰 개수를 표시
    }


  })
  .catch(error => {
    console.error('리뷰 개수를 불러오는 중 에러 발생:', error);
  });
}


function drawStars(rating) {
  let starsHtml = '';
  for (let i = 1; i <= 5; i++) {
    if (i <= rating) {
      starsHtml += '<span class="star filled">&#9733;</span>'; // 채워진 별
    } else {
      starsHtml += '<span class="star">&#9734;</span>'; // 빈 별
    }
  }
  return starsHtml;
}





