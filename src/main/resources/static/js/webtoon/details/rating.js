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
  });
});
