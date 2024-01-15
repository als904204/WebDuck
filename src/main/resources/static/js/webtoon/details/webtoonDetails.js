document.addEventListener('DOMContentLoaded', function () {
  // 이 부분은 DOM이 로드된 후에 실행됩니다.

  // submitReviewButton에 대한 이벤트 리스너 추가
  const submitReviewButton = document.getElementById('submitReviewButton');
  if (submitReviewButton) {
    submitReviewButton.addEventListener('click', function (event) {
      event.preventDefault();
      submitReview();
    });
  }
  // 리뷰 목록 불러오기를 위한 코드 수정
  const webtoonIdElement = document.getElementById('webtoonIdForList');
  if (webtoonIdElement) {
    const webtoonId = webtoonIdElement.value;
    refreshReviewList(webtoonId);
  }
});

function submitReview() {
  const webtoonId = document.getElementById('webtoonId').value;
  const content = document.getElementById('content').value;
  const csrfToken = document.querySelector("input[name='_csrf']").value;
  const rating = selectedStarValue;

  // 입력값 검증
  if (!webtoonId || !content || !rating) {
    let missingFields = [];

    if (!webtoonId) missingFields.push("webtoon ID");
    if (!content) missingFields.push("리뷰 내용");
    if (!rating) missingFields.push("평점");

    // 누락된 필드가 있을 경우 경고 메시지를 표시
    alert(missingFields.join(", ") + "을 입력해주세요");
    return; // 검증 실패시 함수 종료
  }

  fetch('/api/v1/review', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-CSRF-TOKEN': csrfToken
    },
    body: JSON.stringify({
      webtoonId: webtoonId,
      content: content ,
      rating : rating
    })
  })
  .then(response => response.json())
  .then(data => {
    // 리뷰 목록 새로고침
    refreshReviewList(webtoonId);
    // 입력 필드 초기화
    document.getElementById('content').value = '';
  })
  .catch(error => console.error('Error:', error));
}

function refreshReviewList(webtoonId) {
  fetch(`/api/v1/review/${webtoonId}`)
  .then(response => response.json())
  .then(reviews => {
    const reviewListElement = document.getElementById('reviewList');
    if (reviewListElement) {
      reviewListElement.innerHTML = ''; // 기존 내용을 비움

      reviews.forEach(review => {
        const reviewItem = document.createElement('div');
        reviewItem.className = 'list-group-item list-group-item-action flex-column align-items-start';
        reviewItem.innerHTML = `
                    <div class="d-flex w-100 justify-content-between">
                        <p class="mb-1">${review.content}</p>
                        <small class="text-muted">${review.reviewerNickname}</small>
                        <small class="text-muted">${review.rating}</small>
                    </div>
                `;
        reviewListElement.appendChild(reviewItem);
      });
    }
  })
  .catch(error => console.error('Error:', error));
}
