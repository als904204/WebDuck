function uploadWebtoon() {
  const formData = new FormData(document.getElementById('uploadForm'));
  const csrfToken = document.querySelector("input[name='_csrf']").value;

  $.ajax({
    type: `POST`,
    url: `/api/v1/admin/webtoon`,
    data: formData,
    processData: false,
    contentType: false,
    beforeSend: function(xhr) {
      xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
    },
    success: function(response) {
      console.log("Response:", response);
      $('#uploadSuccessModal').modal('show');

      // 업로드 후 확인 버튼 누르면 "/" 로 리턴
      $('#viewWebtoon').on('click', function() {
        window.location.href = '/';
      });
    },
    error: function(xhr, status, error) {
      console.error("Error:", error);
    }
  });
}


document.getElementById('imageFile').addEventListener('change', function(event) {
  const file = event.target.files[0];
  const reader = new FileReader();

  reader.onload = function(e) {
    const imagePreview = document.getElementById('imagePreview');
    imagePreview.src = e.target.result;
    imagePreview.style.display = 'block'; // 이미지 미리보기 표시
  };

  // 파일 읽기
  if (file) {
    reader.readAsDataURL(file);
  } else {
    imagePreview.style.display = 'none'; // 이미지가 없는 경우 미리보기 숨김
  }
});
