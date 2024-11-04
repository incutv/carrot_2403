// 기본 위도 경도 설정
let jhtaLat = 37.572927;
let jhtaLon = 126.992337;
var locPosition;
var lat, lon; // 사용자 현 위치

// 마커 이미지 생성
var imageSrc = "/images/icons/marker.svg";
var imageSize = new kakao.maps.Size(34, 39);
var imageOption = { offset: new kakao.maps.Point(17, 39) };
var markerImage;

var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(jhtaLat, jhtaLon),
        level: 4
    };
var map = new kakao.maps.Map(mapContainer, mapOption);

// 전시장 클러스터러
var exhibitionClusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 2, // 전시장은 줌 레벨 5 이상에서 클러스터링
    gridSize: 80 // 클러스터가 형성되는 영역의 크기 (기본값 60에서 증가)
});

// 공연장 클러스터러
var performanceClusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 2, // 공연장은 줌 레벨 5 이상에서 클러스터링
    gridSize: 200 // 클러스터가 형성되는 영역의 크기 (기본값 60에서 증가)
});

// 현재 내위치
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        var locPosition = new kakao.maps.LatLng(lat, lon);
        userMarker(locPosition, '/images/icons/currentlocation.svg');
    });
} else {
    locPosition = new kakao.maps.LatLng(jhtaLat, jhtaLon);
    userMarker(locPosition, '/images/icons/currentlocation.svg');
}

// 현재 내 위치 마커를 생성하는 함수
function userMarker(locPosition, imageSrcUrl) {
    markerImage = new kakao.maps.MarkerImage(imageSrcUrl, imageSize, imageOption);
    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
        image: markerImage
    });
    map.setCenter(locPosition);
}

// 사용자 현 위치로 이동하는 버튼
function setCurrentLocation() {
    var moveLatLon = new kakao.maps.LatLng(lat, lon);
    map.panTo(moveLatLon);
}

// 오버레이 콘텐츠 생성 함수
function createContent(type, data) {
    const wrap = document.createElement('div');
    wrap.className = 'wrap';

    const info = document.createElement('div');
    info.className = 'info';

    const title = document.createElement('div');
    title.className = 'title';
    title.innerHTML = data.name;

    const closeBtn = document.createElement('div');
    closeBtn.className = 'close';
    closeBtn.title = '닫기';

    title.appendChild(closeBtn);
    info.appendChild(title);

    const body = document.createElement('div');
    body.className = 'body';

    const imgDiv = document.createElement('div');
    imgDiv.className = 'img';
    const img = document.createElement('img');
    img.src = type === 'exhibition' ? "/images/accommdation-smaple.png" : "/images/accommdation-smaple.png"; // 이미지 URL 설정
    img.width = 73;
    img.height = 70;
    imgDiv.appendChild(img);

    const desc = document.createElement('div');
    desc.className = 'desc';
    const ellipsis = document.createElement('div');
    ellipsis.className = 'ellipsis';
    ellipsis.innerText = type === 'exhibition' ? data.location : data.venue;

    const performanceInfo = document.createElement('div');
    performanceInfo.className = 'performance-info';
    performanceInfo.innerText = type === 'exhibition' ? data.theme : data.date;

    const ticketLinkDiv = document.createElement('div');
    const ticketLink = document.createElement('a');
    ticketLink.href = "https://www.kakaocorp.com/main"; // 티켓 예매 링크
    ticketLink.target = "_blank";
    ticketLink.className = 'link';
    ticketLink.innerText = "숙소 예매하기"; // 링크 텍스트

    ticketLinkDiv.appendChild(ticketLink); // 링크를 div에 추가

    desc.appendChild(ellipsis);
    desc.appendChild(performanceInfo);
    desc.appendChild(ticketLinkDiv); // desc에 티켓 링크 추가
    body.appendChild(imgDiv);
    body.appendChild(desc);
    info.appendChild(body);
    wrap.appendChild(info);
    return wrap; // DOM 요소 반환
}

// 전시회 콘텐츠 생성
function createExhibitionContent(exhibition) {
    return createContent('exhibition', exhibition);
}

// 공연 콘텐츠 생성
function createPerformanceContent(performance) {
    return createContent('performance', performance);
}

// 오버레이 생성 함수
function createOverlay(content, map, position) {
    const overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: null,
        position: position
    });

    // Close button 클릭 시 오버레이 닫기
    const closeBtn = content.querySelector('.close');
    closeBtn.addEventListener('click', function () {
        closeOverlay(overlay);
    });

    return overlay; // 생성된 오버레이 반환
}

// 마커 생성 함수
function createTicketMarker(position, imageScrUrl, map, overlay) {
    markerImage = new kakao.maps.MarkerImage(imageScrUrl, imageSize, imageOption);
    var marker = new kakao.maps.Marker({
        position: position,
        image: markerImage,
        map: map
    });

    // 오버레이를 마커에 연결
    marker.kakaoOverlay = overlay;

    return marker;
}

// 오버레이 닫기 함수
function closeOverlay(overlay) {
    if (overlay) {
        overlay.setMap(null); // 오버레이를 지도에서 제거
    } else {
        console.error("오버레이가 존재하지 않습니다.");
    }
}

var exhibitionMarkers = []; // 전시회 마커 저장
var performanceMarkers = []; // 공연장 마커 저장

// 데이터 가져오기
fetch("/test/accommodation-data")
    .then(response => response.json())
    .then(data => {
        data.exhibitions.forEach(exhibition => {
            const position = new kakao.maps.LatLng(exhibition.latitude, exhibition.longitude);
            const overlayContent = createExhibitionContent(exhibition);
            const overlay = createOverlay(overlayContent, map, position);
            const marker = createTicketMarker(position, "/images/icons/exh.svg", map, overlay); // 오버레이를 전달

            // 마커 클릭 시 오버레이 표시
            kakao.maps.event.addListener(marker, 'click', function () {
                overlay.setMap(map);
            });

            exhibitionMarkers.push(marker); // 전시회 마커 저장
            exhibitionClusterer.addMarker(marker); // 클러스터러에 마커 추가
        });

        data.performances.forEach(performance => {
            const position = new kakao.maps.LatLng(performance.latitude, performance.longitude);
            const overlayContent = createPerformanceContent(performance);
            const overlay = createOverlay(overlayContent, map, position);
            const marker = createTicketMarker(position, "/images/icons/marker.svg", map, overlay); // 오버레이를 전달

            // 마커 클릭 시 오버레이 표시
            kakao.maps.event.addListener(marker, 'click', function () {
                overlay.setMap(map);
            });

            performanceMarkers.push(marker); // 공연장 마커 저장
            performanceClusterer.addMarker(marker); // 클러스터러에 마커 추가
        });
    })
    .catch(error => console.error('Error fetching ticket data:', error));

// 마커 필터링 함수
function filterMarkers() {
    const filterValue = document.getElementById('markerFilter').value;

    // 클러스터러 초기화
    exhibitionClusterer.clear();
    performanceClusterer.clear();

    // 모든 오버레이를 닫습니다.
    exhibitionMarkers.forEach(marker => {
        if (marker.kakaoOverlay) {
            marker.kakaoOverlay.setMap(null); // 오버레이를 지도에서 제거합니다.
        }
    });

    performanceMarkers.forEach(marker => {
        if (marker.kakaoOverlay) {
            marker.kakaoOverlay.setMap(null); // 오버레이를 지도에서 제거합니다.
        }
    });

    // 마커를 필터링하여 클러스터러에 추가합니다.
    if (filterValue === 'exhibitions') {
        exhibitionClusterer.addMarkers(exhibitionMarkers); // 전시회 마커만 표시
    } else if (filterValue === 'performances') {
        performanceClusterer.addMarkers(performanceMarkers); // 공연장 마커만 표시
    } else {
        exhibitionClusterer.addMarkers(exhibitionMarkers); // 모든 마커 표시
        performanceClusterer.addMarkers(performanceMarkers);
    }
}

// 이벤트 리스너 추가
document.getElementById('markerFilter').addEventListener('change', filterMarkers);
