function searchCategory(category) {
    const ps = new kakao.maps.services.Places();
    ps.categorySearch(category, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => {
                const coords = new kakao.maps.LatLng(place.y, place.x);
                const marker = addMarker(coords, place.place_name, false, true);
                marker.category = category; // 마커에 카테고리 저장
                placeMarkers.push(marker); // 추천 장소 마커 저장

                // Add place to the list
                const li = document.createElement('li');
                li.className = 'place-item';
                li.innerHTML = `
                    <div class="place-name">${place.place_name}</div>
                    <div class="place-info" style="display: none;">
                        <p>주소: ${place.road_address_name || place.address_name}</p>
                        <p>전화번호: ${place.phone || '정보 없음'}</p>
                        <p>리뷰: ${place.review || '리뷰 없음'}</p>
                    </div>
                `;
                li.dataset.category = category;
                li.dataset.latlng = JSON.stringify(coords);
                document.getElementById('place-list').appendChild(li);

                // Add click event to the marker
                kakao.maps.event.addListener(marker, 'click', () => {
                    closeAllPlaceInfos(); // 모든 장소 정보 창 닫기
                    displayPlaceInfo(li, place);
                    map.panTo(coords); // 마커 위치로 맵 포커스 이동
                    focusOnListItem(li); // 목록 아이템으로 포커스 이동
                });

                // Add click event to the list item
                li.querySelector('.place-name').addEventListener('click', () => {
                    closeAllPlaceInfos(); // 모든 장소 정보 창 닫기
                    displayPlaceInfo(li, place);
                    map.panTo(coords); // 목록 클릭 시 마커 위치로 맵 포커스 이동
                });
            });
        }
    }, { location: map.getCenter(), radius: 500 });
}
