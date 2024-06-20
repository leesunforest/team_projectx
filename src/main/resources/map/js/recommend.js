function recommendPlaces(midpoint) {
    const ps = new kakao.maps.services.Places();
    const keywords = ['FD6', 'CE7'];
    const placeList = document.getElementById('place-list');
    placeList.innerHTML = '';
    document.getElementById('left-panel').style.display = 'block';
    placeMarkers = [];

    keywords.forEach(keyword => {
        ps.categorySearch(keyword, (data, status) => {
            if (status === kakao.maps.services.Status.OK) {
                data.forEach(place => {
                    const latlng = new kakao.maps.LatLng(place.y, place.x);
                    const marker = addMarker(latlng, place.place_name, false, true);
                    marker.category = keyword;
                    placeMarkers.push(marker);

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
                    li.dataset.category = keyword;
                    li.dataset.latlng = JSON.stringify(latlng);
                    placeList.appendChild(li);

                    kakao.maps.event.addListener(marker, 'click', () => {
                        closeAllPlaceInfos();
                        displayPlaceInfo(li, place);
                        map.panTo(latlng);
                        focusOnListItem(li);
                    });

                    li.querySelector('.place-name').addEventListener('click', () => {
                        closeAllPlaceInfos();
                        displayPlaceInfo(li, place);
                        map.panTo(latlng);
                    });
                });
            }
        }, { location: midpoint, radius: 500 });
    });
}

function focusOnListItem(listItem) {
    document.getElementById('left-panel').scrollTop = listItem.offsetTop;
}

function displayPlaceInfo(listItem, place) {
    listItem.querySelector('.place-info').style.display = 'block';
}

function closeAllPlaceInfos() {
    document.querySelectorAll('.place-info').forEach(info => {
        info.style.display = 'none';
    });
}
