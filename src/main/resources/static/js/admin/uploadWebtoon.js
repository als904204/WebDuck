// 웹툰 업로드 함수
function uploadWebtoon() {
  const formData = new FormData(document.getElementById('uploadForm'));
  const csrfToken = document.querySelector("input[name='_csrf']").value;

  // 입력 필드 검증
  if (!validateFormData(formData)) {
    return; // 유효성 검증 실패 시, 함수 종료
  }

  for (let [key, value] of formData.entries()) {
    console.log(key, value);
  }

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
      $('#viewWebtoon').on('click', function() {
        window.location.href = '/';
      });
    },
    error: function(xhr, status, error) {
      handleUploadError(xhr, status, error);
    }
  });
}

// 이미지 파일 미리보기 기능
document.getElementById('imageFile').addEventListener('change', function(event) {
  const file = event.target.files[0];
  const reader = new FileReader();
  const imagePreview = document.getElementById('imagePreview');

  reader.onload = function(e) {
    imagePreview.src = e.target.result;
    imagePreview.style.display = 'block';
  };

  if (file) {
    reader.readAsDataURL(file);
  } else {
    imagePreview.style.display = 'none';
  }
});

// 입력 데이터 유효성 검증
function validateFormData(formData) {
  const requiredFields = ['title', 'summary', 'publishDay', 'platform'];
  for (let field of requiredFields) {
    if (!formData.has(field) || formData.get(field).trim() === '') {
      alert(convertToUserFriendlyFieldName(field) + " 필드를 채워주세요.");
      return false;
    }
  }

  if (!validateGenres(formData)) {
    alert("장르를 하나 이상 선택해주세요.");
    return false;
  }

  return true;
}

// 장르 필드 유효성 검증
function validateGenres(formData) {
  // 'genreName[]' 필드의 체크된 항목의 수를 확인
  const genres = formData.getAll('genreName[]');
  return genres.length > 0;
}
// 필드 이름을 사용자 친화적인 이름으로 변환
function convertToUserFriendlyFieldName(field) {
  const fieldNames = {
    title: '제목',
    summary: '줄거리',
    imageFile: '이미지 파일',
    publishDay: '요일',
    genreName: '장르',
    platform: '플랫폼'
  };
  return fieldNames[field] || field;
}

// 업로드 에러 처리
function handleUploadError(xhr, status, error) {
  if (xhr.status === 400) {
    alert("입력값을 확인해주세요.");
  } else if (xhr.status === 413) {
    alert("이미지 용량 최대 크기는 1MB 입니다");
  } else {
    alert("알 수 없는 에러 발생: " + xhr.status);
    console.error("Error:", error);
  }
}
