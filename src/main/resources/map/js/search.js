// search.js
import { map } from './map.js';
import { addMarker } from './marker.js';

export function filterPlaces(category) {
    markers.forEach(marker => {
        if (marker.isUser || marker.isMidpoint) {
            marker.setVisible(true); // 유저 마커와 중간지점 마커는 항상 보이도록 설정
        } else if (marker.category === category) {
            marker.setVisible(true);
        } else {
            marker.setVisible(false);
        }
    });

    const placeList = document.getElementById('place-list');
    const items = placeList.getElementsByTagName('li');
    Array.from(items).forEach(item => {
        if (item.dataset.category === category) {
            item.style.display = 'list-item';
        } else {
            item.style.display = 'none';
        }
    });
}

export function searchCategory(category) {
    const ps = new kakao.maps.services.Places();

    clearMarkers();

    ps.categorySearch(category, (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => {
                const coords = new kakao.maps.LatLng(place.y, place.x);
                addMarker(coords, place.place_name, false, true, false, category);

                const li = document.createElement('li');
                li.textContent = place.place_name;
                li.dataset.category = category;
                li.dataset.id = place.id;
                placeList.appendChild(li);

                kakao.maps.event.addListener(marker, 'mouseover', () => {
                    const content = `
                        <div style="padding:5px;">
                            <h5>${place.place_name}</h5>
                            <p>${category === 'FD6' ? '음식점' : '카페'}</p>
                            <p>${place.address_name}</p>
                            <p>리뷰: ${place.review || '없음'}</p>
                        </div>`;
                    infowindow.setContent(content);
                    infowindow.open(map, marker);
                });

                kakao.maps.event.addListener(marker, 'mouseout', () => {
                    infowindow.close();
                });

                kakao.maps.event.addListener(marker, 'click', () => {
                    alert(`Place: ${place.place_name}\nAddress: ${place.road_address_name || place.address_name}`);
                });
            });
        }
    }, { bounds: map.getBounds() });
}

export function searchAddrFromKeyword(input, index) {
    const keywordList = document.getElementById(`keywordList${index}`);
    keywordList.innerHTML = '';

    const ps = new kakao.maps.services.Places();
    const keyword = input.value;

    if (!keyword.trim()) {
        return;
    }

    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            keywordList.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.place_name}</strong><br><small id="keyword-address-${index}-${item.id}">Loading address...</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    input.value = item.place_name;  // Show keyword
                    input.dataset.fullAddress = '';  // Placeholder for full address
                    keywordList.innerHTML = '';
                    getFullAddress(item, `keyword-address-${index}-${item.id}`, input);
                };
                keywordList.appendChild(div);

                // Fetch and display full address
                getFullAddress(item, `keyword-address-${index}-${item.id}`);
            });
        } else {
            performAddressSearch(keyword, keywordList, input);
        }
    }, { bounds: map.getBounds() });
}

export function searchAddress(input) {
    const keywordList = document.getElementById('address-search-results');
    keywordList.innerHTML = '';

    const ps = new kakao.maps.services.Places();
    const keyword = input.value;

    if (!keyword.trim()) {
        return;
    }

    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            keywordList.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.place_name}</strong><br><small id="address-${item.id}">Loading address...</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    const modal = document.getElementById('address-search-modal');  // Modal 참조 추가
                    const originalInput = document.getElementById(modal.dataset.inputId);
                    originalInput.value = item.place_name;  // Show keyword
                    originalInput.dataset.fullAddress = '';  // Placeholder for full address
                    keywordList.innerHTML = '';
                    closeAddressSearchModal();
                    getFullAddress(item, `address-${item.id}`, originalInput);
                };
                keywordList.appendChild(div);

                // Fetch and display full address
                getFullAddress(item, `address-${item.id}`);
            });
        }
    }, { bounds: map.getBounds() });
}
