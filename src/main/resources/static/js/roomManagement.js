const userId = 3; // 실제 사용자 ID를 여기에 설정
window.userId = userId; // window 객체에 userId 저장

console.log(`사용자 ID: ${userId}`);

function goToRegisterPage() {
  window.location.href = '/host/room/register';
}

document.addEventListener("DOMContentLoaded", function () {
  const userId = window.userId; // HTML에서 정의한 userId를 가져옴

  console.log(`Calling API with userId: ${userId}`);
  fetch(`/api/host/room/manage/${userId}`) // API 호출
  .then(response => {
    if (!response.ok) {
      throw new Error("네트워크 응답이 올바르지 않습니다.");
    }
    return response.json(); // JSON 데이터로 변환
  })
  .then(accommodations => {
    console.log(accommodations);
    const roomList = document.getElementById('roomList');
    roomList.innerHTML = ''; // 기존 내용을 지우고 새로 추가

    accommodations.forEach(accommodation => {
      const listItem = document.createElement('li');
      listItem.innerHTML = `
                    <dl class="room_item">
                        <dt>
                            <img src="${accommodation.imageUrl}" alt="방 이미지">
                        </dt>
                        <dd>
                            <p class="room_name ellipsis">
                                <a class="disable_preview">${accommodation.title}</a>
                            </p>
                            <p class="room_address">${accommodation.lotAddress} ${accommodation.detailAddress}</p>
                            <div class="room_pay">
                                <p>
                                    <strong>${accommodation.price}</strong><em>원</em>
                                    <span>/1박</span>
                                </p>
                            </div>
                        </dd>
                    </dl>
                    
                    <div class="drop_down">
                        <button type="button" class="btn_drop"><span>더보기</span></button>
                        <div class="drop_layer">
                            <ul class="btn_more">
                                <li class="icon_more_modify"><a data-room-modify data-id="${accommodation.id}">수정하기</a></li>
                                <li class="icon_more_del"><a data-room-delete data-id="${accommodation.id}">삭제하기</a></li>
                            </ul>
                        </div>
                    </div>
                `;
      roomList.appendChild(listItem);

      // 이벤트 리스너 추가
      const toggleButton = listItem.querySelector(".btn_drop");
      const dropDown = listItem.querySelector(".drop_layer");

      toggleButton.addEventListener("click", function () {
        const isActive = dropDown.parentElement.classList.toggle("is_active");
        dropDown.style.display = isActive ? "block" : "none"; // 드롭다운 보이기/숨기기
      });

      // 수정하기 버튼 클릭 이벤트 추가
      const modifyButton = listItem.querySelector('[data-room-modify]');
      modifyButton.addEventListener("click", function () {
        const accommodationId = modifyButton.dataset.id; // 방 ID 가져오기
        window.location.href = `/host/room/edit/${accommodationId}`; // 수정 폼으로 이동
      });

      // 삭제하기 버튼 클릭 이벤트 추가
      const deleteButton = listItem.querySelector('[data-room-delete]');
      deleteButton.addEventListener("click", function () {
        const accommodationId = deleteButton.dataset.id; // 방 ID 가져오기
        // 삭제 로직 추가
      })
    });
  })
  .catch(error => {
    console.error('숙소 정보를 가져오는 중 오류 발생: ', error);
  });
});
