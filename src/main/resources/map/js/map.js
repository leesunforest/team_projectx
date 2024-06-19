document.addEventListener('DOMContentLoaded', () => {
    initializeMap();

    document.getElementById('groupButton').addEventListener('click', openGroupModal);
    document.querySelector('.close').addEventListener('click', closeGroupModal);
    document.querySelector('.close-address-modal').addEventListener('click', closeAddressSearchModal);
    document.getElementById('addGroup').addEventListener('click', addGroupInput);
    document.getElementById('findMidpoint').addEventListener('click', findMidpoint);

    document.querySelectorAll('.category').forEach(button => {
        button.addEventListener('click', () => searchCategory(button.dataset.category));
    });
});

let map;
let markers = [];
let locations = [];
let userLocation = null;

const midpointMarkerIcon = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png'; // 중간지점 마커 이미지 URL
const starMarkerIcon = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'; // 추천 장소 마커 이미지 URL

function initializeMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position => {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;
            userLocation = new kakao.maps.LatLng(lat, lng); // 사용자 위치 저장
            const mapContainer = document.getElementById('map');
            const mapOption = {
                center: userLocation,
                level: 3
            };
            map = new kakao.maps.Map(mapContainer, mapOption);

            kakao.maps.event.addListener(map, 'idle', () => {});
        }, () => {
            const mapContainer = document.getElementById('map');
            const mapOption = {
                center: new kakao.maps.LatLng(37.5665, 126.9780),
                level: 3
            };
            map = new kakao.maps.Map(mapContainer, mapOption);
        });
    } else {
        const mapContainer = document.getElementById('map');
        const mapOption = {
            center: new kakao.maps.LatLng(37.5665, 126.9780),
            level: 3
        };
        map = new kakao.maps.Map(mapContainer, mapOption);
    }
}

function openGroupModal() {
    document.getElementById('group-modal').style.display = 'flex';
}

function closeGroupModal() {
    document.getElementById('group-modal').style.display = 'none';
}

function openAddressSearchModal(input) {
    const modal = document.getElementById('address-search-modal');
    modal.dataset.inputId = input.id;
    modal.style.display = 'flex';
}

function closeAddressSearchModal() {
    document.getElementById('address-search-modal').style.display = 'none';
}

function addGroupInput() {
    const groupInputs = document.getElementById('groupInputs');
    const index = groupInputs.getElementsByClassName('group-input').length + 1;
    const newInput = document.createElement('div');
    newInput.className = 'group-input';
    newInput.innerHTML = `
            <label for="group${index}">유저 ${index}:</label>
            <input type="text" id="group${index}" class="group" placeholder="주소 또는 키워드 입력" name="group${index}" readonly onclick="openAddressSearchModal(this)">
            <div id="keywordList${index}" class="keyword-list"></div>
            <button class="remove-button" onclick="removeGroupInput(this)">X</button>
        `;
    groupInputs.appendChild(newInput);
    ensureMinimumOneUser();
}

function removeGroupInput(button) {
    const groupInput = button.parentElement;
    groupInput.remove();
    renumberUsers();
    ensureMinimumOneUser();
}

function renumberUsers() {
    const groupInputs = document.getElementById('groupInputs').getElementsByClassName('group-input');
    Array.from(groupInputs).forEach((input, index) => {
        const label = input.querySelector('label');
        const inputField = input.querySelector('input');
        label.setAttribute('for', `group${index + 1}`);
        label.textContent = `유저 ${index + 1}:`;
        inputField.id = `group${index + 1}`;
        inputField.name = `group${index + 1}`;
        inputField.setAttribute('onclick', `openAddressSearchModal(this)`);
    });
}

function ensureMinimumOneUser() {
    const groupInputs = document.getElementById('groupInputs').getElementsByClassName('group-input');
    if (groupInputs.length === 0) {
        addGroupInput();
    }
}

function findMidpoint() {
    const groupInputs = document.getElementsByClassName('group');
    if (groupInputs.length < 2) {
        alert('두 개 이상의 위치를 입력해 주세요.');
        return;
    }

    const promises = [];
    locations = [];

    for (let i = 0; i < groupInputs.length; i++) {
        const input = groupInputs[i];
        const query = input.value;
        promises.push(
            new Promise((resolve, reject) => {
                const geocoder = new kakao.maps.services.Geocoder();
                geocoder.addressSearch(query, (result, status) => {
                    if (status === kakao.maps.services.Status.OK && result.length > 0) {
                        const latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
                        locations.push({ latlng, name: `user${i + 1}` });
                        resolve();
                    } else {
                        reject(`주소를 찾을 수 없습니다: ${query}`);
                    }
                });
            })
        );
    }

    Promise.all(promises)
        .then(() => {
            if (locations.length > 1) {
                const midpoint = calculateGreatCircleMidpoint(locations);

                clearMarkers();
                locations.forEach(location => addMarker(location.latlng, location.name, false, false, true));
                addMarker(midpoint, '중간지점', true);

                map.setCenter(midpoint);

                recommendPlaces(midpoint);
            }
        })
        .catch(error => {
            alert(error);
        });
}

function addMarker(position, title, isMidpoint = false, isRecommendedPlace = false, isUser = false) {
    let markerImage = null;
    if (isMidpoint) {
        markerImage = new kakao.maps.MarkerImage(
            midpointMarkerIcon,
            new kakao.maps.Size(24, 35)
        );
    } else if (isRecommendedPlace) {
        markerImage = new kakao.maps.MarkerImage(
            starMarkerIcon,
            new kakao.maps.Size(24, 35)
        );
    }

    const marker = new kakao.maps.Marker({
        map: map,
        position: position,
        title: title,
        image: markerImage
    });

    if (isUser) {
        const infowindow = new kakao.maps.InfoWindow({
            content: `<div style="padding:5px;white-space:nowrap;">${title}</div>`
        });
        infowindow.open(map, marker);
    }

    markers.push(marker);
}

function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

function recommendPlaces(midpoint) {
    const ps = new kakao.maps.services.Places();
    const keywords = ['식당', '카페'];
    keywords.forEach(keyword => {
        ps.keywordSearch(keyword, (data, status) => {
            if (status === kakao.maps.services.Status.OK) {
                data.forEach(place => {
                    const latlng = new kakao.maps.LatLng(place.y, place.x);
                    addMarker(latlng, place.place_name, false, true);
                });
            }
        }, { location: midpoint, radius: 500 });
    });
}

function searchAddrFromKeyword(input, index) {
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
                    const originalValue = input.value;
                    input.value = item.place_name;
                    input.dataset.fullAddress = '';  // Placeholder for full address
                    keywordList.innerHTML = '';
                    getAddressFromCoords(new kakao.maps.LatLng(item.y, item.x), input, originalValue);
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



function getAddressFromCoords(coords, input, originalValue) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
            const address = result[0].address.address_name;
            if (input) {
                input.dataset.fullAddress = address;
                input.value = originalValue;
            }
        }
    });
}


function performAddressSearch(keyword, keywordList, input) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.addressSearch(keyword, (result, status) => {
        if (status === kakao.maps.services.Status.OK && result.length > 0) {
            keywordList.innerHTML = '';
            result.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.address_name}</strong><br><small>${item.address_name}</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    input.value = item.address_name;
                    keywordList.innerHTML = '';
                };
                keywordList.appendChild(div);
            });
        } else {
            keywordList.innerHTML = '';
            const div = document.createElement('div');
            div.className = 'autocomplete-item';
            div.textContent = 'No results found';
            keywordList.appendChild(div);
        }
    });
}


function searchAddress(input) {
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
                    const originalInput = document.getElementById(document.getElementById('address-search-modal').dataset.inputId);
                    originalInput.value = item.place_name;
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

function getFullAddress(item, elementId, originalInput = null) {
    const geocoder = new kakao.maps.services.Geocoder();
    const coords = new kakao.maps.LatLng(item.y, item.x);
    geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
        if (status === kakao.maps.services.Status.OK && result.length > 0) {
            const address = result[0].address.address_name;
            document.getElementById(elementId).textContent = address;
            if (originalInput) {
                originalInput.dataset.fullAddress = address;
            }
        } else {
            document.getElementById(elementId).textContent = '주소를 찾을 수 없습니다.';
        }
    });
}

function searchCategory(category) {
    const ps = new kakao.maps.services.Places();

    clearMarkers();

    ps.categorySearch(category, (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => {
                const coords = new kakao.maps.LatLng(place.y, place.x);
                addMarker(coords, place.place_name);
                getAddressFromCoords(coords, null);
            });
        }
    }, { bounds: map.getBounds() });
}

function getAddressFromCoords(coords, input) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
            const address = result[0].address.address_name;
            if (input) {
                input.value = address;
            }
        }
    });
}

function searchAddrFromCoords(coords, callback) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

function searchDetailAddrFromCoords(coords, callback) {
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

function toRadians(degrees) {
    return degrees * Math.PI / 180;
}

function toDegrees(radians) {
    return radians * 180 / Math.PI;
}

function calculateGreatCircleMidpoint(locations) {
    const total = locations.length;
    let X = 0;
    let Y = 0;
    let Z = 0;

    locations.forEach(location => {
        const lat = toRadians(location.latlng.getLat());
        const lon = toRadians(location.latlng.getLng());

        X += Math.cos(lat) * Math.cos(lon);
        Y += Math.cos(lat) * Math.sin(lon);
        Z += Math.sin(lat);
    });

    X /= total;
    Y /= total;
    Z /= total;

    const Lon = Math.atan2(Y, X);
    const Hyp = Math.sqrt(X * X + Y * Y);
    const Lat = Math.atan2(Z, Hyp);

    return new kakao.maps.LatLng(toDegrees(Lat), toDegrees(Lon));
}
