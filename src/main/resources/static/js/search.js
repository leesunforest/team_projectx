function handleAddressSearchKeyDown(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevent form submission if inside a form
        executeAddressSearch();
    }
}

function executeAddressSearch() {
    const input = document.getElementById('address-search-input');
    const keywordList = document.getElementById('address-search-results');
    keywordList.innerHTML = '';

    const ps = new kakao.maps.services.Places();
    const keyword = input.value.trim();
    if (!keyword) return;

    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            sortAndDisplayResults(data, keywordList);
        } else {
            ps.categorySearch(keyword, (data, status) => {
                if (status === kakao.maps.services.Status.OK && data.length > 0) {
                    sortAndDisplayResults(data, keywordList);
                } else {
                    performAddressSearch(keyword, keywordList, input);
                }
            });
        }
    });
}

function sortAndDisplayResults(data, keywordList) {
    if (userLocation) {
        data.sort((a, b) => {
            const distanceA = calculateDistance(userLocation, new kakao.maps.LatLng(a.y, a.x));
            const distanceB = calculateDistance(userLocation, new kakao.maps.LatLng(b.y, b.x));
            return distanceA - distanceB;
        });
    }

    keywordList.innerHTML = '';
    data.forEach(item => {
        const div = document.createElement('div');
        div.innerHTML = `<strong>${item.place_name || item.address_name}</strong><br><small id="address-${item.id}">Loading address...</small>`;
        div.className = 'autocomplete-item';
        div.onclick = () => {
            const modal = document.getElementById('address-search-modal');
            const originalInput = document.getElementById(modal.dataset.inputId);
            originalInput.value = item.place_name || item.address_name;
            originalInput.dataset.fullAddress = '';
            keywordList.innerHTML = '';
            closeAddressSearchModal();
            getFullAddress(item, `address-${item.id}`, originalInput);
        };
        keywordList.appendChild(div);
        getFullAddress(item, `address-${item.id}`);
    });
}

function getFullAddress(item, elementId, originalInput = null) {
    const geocoder = new kakao.maps.services.Geocoder();
    const coords = new kakao.maps.LatLng(item.y, item.x);
    geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
        if (status === kakao.maps.services.Status.OK && result.length > 0) {
            const address = result[0].address.address_name;
            const element = document.getElementById(elementId);
            if (element) element.textContent = address;
            if (originalInput) originalInput.dataset.fullAddress = address;
        } else {
            const element = document.getElementById(elementId);
            if (element) element.textContent = '주소를 찾을 수 없습니다.';
        }
    });
}

function performAddressSearch(keyword, keywordList, input) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.addressSearch(keyword, (result, status) => {
        if (status === kakao.maps.services.Status.OK && result.length > 0) {
            sortAndDisplayResults(result, keywordList);
        } else {
            keywordList.innerHTML = '<div class="autocomplete-item">No results found</div>';
        }
    });
}

function useCurrentLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position => {
            const geocoder = new kakao.maps.services.Geocoder();
            const coords = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
            geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    const address = result[0].address.address_name;
                    const input = document.getElementById('address-search-input');
                    input.value = address;
                    const keywordList = document.getElementById('address-search-results');
                    keywordList.innerHTML = ''; // Clear any existing results
                } else {
                    alert('현재 위치의 주소를 찾을 수 없습니다.');
                }
            });
        }, () => {
            alert('현재 위치를 가져올 수 없습니다.');
        });
    } else {
        alert('Geolocation을 지원하지 않는 브라우저입니다.');
    }
}

function searchAndRecommendPlaces(center) {
    const categories = ['FD6', 'CE7'];
    const placeList = document.getElementById('place-list');
    placeList.innerHTML = '';
    document.getElementById('left-panel').style.display = 'block';
    placeMarkers = [];

    categories.forEach(category => {
        searchCategoryAroundCenter(category, center);
    });
}

function searchCategoryAroundCenter(category, center) {
    clearMarkers(); // 기존 마커 제거
    const ps = new kakao.maps.services.Places();
    ps.categorySearch(category, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => {
                const latlng = new kakao.maps.LatLng(place.y, place.x);
                const marker = addMarker(latlng, place.place_name, false, true);
                marker.category = category;
                placeMarkers.push(marker);

                const li = document.createElement('li');
                li.className = 'place-item';
                li.innerHTML = `
                    <div class="place-name">${place.place_name}</div>
                    <div class="place-info" style="display: none;">
                        <p>주소: ${place.road_address_name || place.address_name}</p>
                        <p>전화번호: ${place.phone || '정보 없음'}</p>
                    </div>
                `;
                li.dataset.category = category;
                li.dataset.latlng = JSON.stringify(latlng);
                li.dataset.placeId = place.id;
                document.getElementById('place-list').appendChild(li);

                kakao.maps.event.addListener(marker, 'click', () => {
                    closeAllPlaceInfos();
                    displayPlaceInfo(li, place);
                    map.panTo(latlng);
                    focusOnListItem(li);
                    fetchPlaceDetails(place.id, li);
                });

                li.querySelector('.place-name').addEventListener('click', () => {
                    closeAllPlaceInfos();
                    displayPlaceInfo(li, place);
                    map.panTo(latlng);
                    fetchPlaceDetails(place.id, li);
                });
            });
        }
    }, { location: center, radius: 500 });
}

function searchAndDisplayPlaces() {
    clearMarkers(); // 기존 마커 제거
    if (userLocation) {
        searchAndRecommendPlaces(userLocation);
    }
}

function searchAndDisplayPlaces() {
    if (userLocation) {
        searchAndRecommendPlaces(userLocation);
    }
}