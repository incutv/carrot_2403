let geoCoder;

function initMap() {
  geoCoder = new kakao.maps.services.Geocoder();
}

function getAddressCoords(address) {
  return new Promise((resolve, reject) => {
    if (geoCoder) {
      geoCoder.addressSearch(address, (result, status) => {
        if (status === kakao.maps.services.Status.OK && result.length > 0) {
          const coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 위도, 경도
          resolve(coords);
        } else {
          reject('주소 검색 실패: ' + status);
        }
      });
    } else {
      reject('geoCoder가 정의되지 않았습니다.');
    }
  });
}

function sample4_execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      document.getElementById('roadAddress').value = data.roadAddress; // 도로명 주소
      document.getElementById('lotAddress').value = data.jibunAddress; // 지번 주소

      const mainAddress = data.roadAddress || data.jibunAddress;
      getAddressCoords(mainAddress)
      .then(coords => {
        document.getElementById('latitude').value = coords.getLat(); // 위도
        document.getElementById('longitude').value = coords.getLng(); // 경도
      })
      .catch(error => {
        console.error('좌표를 가져오는 중 오류 발생:', error);
        alert('좌표를 가져오는 데 문제가 발생했습니다. 다시 시도해주세요.');
      });
    }
  }).open();
}

document.addEventListener('DOMContentLoaded', () => {
  initMap();
  document.getElementById('btn_search_addr').addEventListener('click',
      sample4_execDaumPostcode);
});

document.getElementById('images').addEventListener('change', function (event) {
  const files = event.target.files;
  const imageContainer = document.getElementById('div_added_pictures');
  imageContainer.innerHTML = ''; // 기존 미리보기 이미지 제거

  Array.from(files).forEach(file => {
    const reader = new FileReader();

    reader.onload = function (e) {
      const imageItem = document.createElement('div');
      imageItem.className = 'image_item';
      imageItem.innerHTML = `<img src="${e.target.result}" alt="미리보기 이미지">`;
      imageContainer.appendChild(imageItem);
    };

    reader.readAsDataURL(file);
  });
});

document.getElementById('registerForm').addEventListener('submit',
    function (event) {
      const checkboxes = document.querySelectorAll('input[type="checkbox"]');
      const isChecked = Array.from(checkboxes).some(
          checkbox => checkbox.checked);

      const imageInput = document.getElementById('images');
      const files = imageInput.files;

      if (files.length < 4) {
        event.preventDefault();
        alert('방 사진은 최소 4장 이상 등록해야 합니다.');
        return;
      }

      if (!isChecked) {
        event.preventDefault();
        alert('기본 옵션을 하나 이상 선택해야 합니다.');
      }
    });

function submitForm(event) {
  event.preventDefault();
  const formData = new FormData(document.getElementById('registerForm'));
  for (let [key, value] of formData.entries()) {
    console.log(`${key}: ${value}`);
  }

  fetch('/api/host/room/register', {
    method: 'POST',
    body: formData
  })
  .then(response => {
    if (!response.ok) {
      return response.json().then(err => {
        // 오류 처리 (예: 오류 메시지 표시)
        console.error('오류 발생:', err);
      });
    }
    return response.json();
  })
  .then(data => {
    console.log('숙소 등록 성공:', data);
    window.location.href = '/host/room/manage';

  })
  .catch(error => {
    console.error('서버 오류:', error);
    alert('서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.');
  });
}

