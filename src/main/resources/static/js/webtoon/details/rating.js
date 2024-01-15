let selectedStarValue = null; // 클릭된 별의 값을 저장할 변수

$(document).ready(function(){

  const star = '.star';


  // 마우스 오버시 별의 색상 변경
  $(star).on('mouseover', function(){
    const onStar = parseInt($(this).data('value'), 10); // 마우스가 올라간 별의 값

    $(this).parent().children(star).each(function(e){
      if (e < onStar) {
        $(this).addClass('hover');
      } else {
        $(this).removeClass('hover');
      }
    });
  }).on('mouseout', function(){
    $(this).parent().children(star).each(function(e){
      $(this).removeClass('hover');
    });
  });

  // 별 클릭시 색상 고정
  $(star).on('click', function(){
    const onStar = parseInt($(this).data('value'), 10); // 클릭된 별의 값

    // 모든 별에 대해 루프를 돌면서 클릭된 별까지 selected 클래스 추가, 그 외는 제거
    $(this).parent().children(star).each(function(e){
      if (e < onStar) {
        $(this).addClass('selected');
      } else {
        $(this).removeClass('selected');
      }
    });
    // 클릭된 별의 값을 변수에 저장
    selectedStarValue = onStar;
    console.log('선택된 별의 값: ' + selectedStarValue); // 콘솔에 출력 예시
  });


  // 별 클릭시 텍스트 출력
  $(star).on('click', function () {
    const onStar = parseInt($(this).data('value'), 10); // 클릭된 별의 값
    const reviewText = displayReviewText(onStar); // 함수 호출

    // 이전 텍스트를 제거하고 새 텍스트를 삽입
    $(this).parent().find('.review-text').remove();
    $(this).parent().append('<div class="review-text">' + reviewText + '</div>');
  });
});

// 별 클릭시 텍스트 리뷰를 표시하는 함수
function displayReviewText(starValue) {
  let reviewText = '';
  if (starValue === 1) {
    reviewText = '아쉬워요';
  } else if (starValue === 2) {
    reviewText = '보통이에요';
  } else if (starValue === 3) {
    reviewText = '재밌어요';
  } else if (starValue === 4) {
    reviewText = '완전 재밌어요';
  } else if (starValue === 5) {
    reviewText = '강추에요';
  }
  return reviewText;
}

