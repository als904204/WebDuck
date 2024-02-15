let currentPage = 0;
let hasNextPage = true;
let isLoading = false;
let nextId = null;

document.addEventListener('DOMContentLoaded', function () {
  const submitReviewButton = document.getElementById('submitReviewButton');
  if (submitReviewButton) {
    submitReviewButton.addEventListener('click', function (event) {
      event.preventDefault();
      submitReview();
    });
  }

  const webtoonIdElement = document.getElementById('webtoonIdForList');
  if (webtoonIdElement) {
    const webtoonId = webtoonIdElement.value;
    refreshReviewList(webtoonId);
    fetchReviewAvg(webtoonId);
    fetchReviewCount(webtoonId);
  }
});

function loadMoreReviews(webtoonId) {
  if (!hasNextPage || isLoading) return;

  isLoading = true;
  let url = `/api/v1/review/${webtoonId}?size=5&page=${currentPage}`;
  if (nextId) url += `&nextId=${nextId}`;

  fetch(url)
  .then(response => response.json())
  .then(slice => {
    if (slice.item && slice.item.length > 0) {
      slice.item.forEach(review => appendReviewItem(review));
      currentPage++;
      nextId = slice.nextId;
      hasNextPage = slice.hasNext;
    }
    isLoading = false;
  })
  .catch(error => {
    console.error('Error:', error);
    isLoading = false;
  });
}

function appendReviewItem(review) {
  const reviewListElement = document.getElementById('reviewList');
  const reviewItem = document.createElement('div');
  reviewItem.className = 'list-group-item flex-column align-items-start';
  const starsDisplay = getStars(review.rating);

  reviewItem.innerHTML = `
        <div class="w-100">
            <small class="text-danger">${starsDisplay}</small>
        </div>
        <p class="mb-1">${review.content}</p>
        <div class="w-100">
            <small class="text-muted">${review.reviewerNickname}</small>
            <small class="text-muted">${review.createdAt}</small>
        </div>`;
  reviewListElement.appendChild(reviewItem);
}

window.addEventListener('scroll', () => {
  if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
    const webtoonIdElement = document.getElementById('webtoonIdForList');
    const webtoonId = webtoonIdElement ? webtoonIdElement.value : null;
    if (webtoonId) {
      loadMoreReviews(webtoonId);
    }
  }
});

function submitReview() {
  const webtoonId = document.getElementById('webtoonId').value;
  const content = document.getElementById('content').value;
  const csrfToken = document.querySelector("input[name='_csrf']").value;
  const rating = selectedStarValue;

  if (!webtoonId || !content || !rating) {
    let missingFields = [];

    if (!webtoonId) missingFields.push("존재하지 않는 웹툰");
    if (!content) missingFields.push("리뷰 내용");
    if (!rating) missingFields.push("평점");

    alert(missingFields.join(", ") + "을 입력해주세요");
    return;
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
    window.alert("리뷰 등록이 완료되었습니다")
    // 리뷰 (목록,평균,리뷰개수) 새로고침
    refreshReviewList(webtoonId);
    fetchReviewAvg(webtoonId);
    fetchReviewCount(webtoonId);
    // 입력 필드 초기화
    document.getElementById('content').value = '';
  })
  .catch(error => console.error('Error:', error));
}


function refreshReviewList(webtoonId) {
  fetch(`/api/v1/review/${webtoonId}`)
  .then(response => response.json())
  .then(slice => {
    const reviewListElement = document.getElementById('reviewList');
    if (reviewListElement && slice.item) {
      reviewListElement.innerHTML = ''; // 기존 내용을 비움
      slice.item.forEach(review => {
        appendReviewItem(review);
      });

      // 현재 페이지 업데이트
      currentPage = 1;
      nextId = slice.nextId; // 다음 리뷰 ID 업데이트
      hasNextPage = slice.hasNext; // 다음 페이지 존재 여부 업데이트
    }
  })
  .catch(error => console.error('Error:', error));
}






