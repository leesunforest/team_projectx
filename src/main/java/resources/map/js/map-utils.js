function findMidpoint() {
    const groupInputs = document.getElementsByClassName('group');
    if (groupInputs.length < 2) {
        alert('두 개 이상의 위치를 입력해 주세요.');
        return;
    }

    locations = [];
    const promises = Array.from(groupInputs).map(input => {
        const query = input.value;
        const fullAddress = input.dataset.fullAddress || query;
        return new Promise((resolve, reject) => {
            new kakao.maps.services.Geocoder().addressSearch(fullAddress, (result, status) => {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    const latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
                    locations.push({ latlng, name: `user${input.id.replace('group', '')}` });
                    resolve();
                } else {
                    reject(`주소를 찾을 수 없습니다: ${fullAddress}`);
                }
            });
        });
    });

    Promise.all(promises)
        .then(() => {
            if (locations.length > 1) {
                let isValid = true;
                for (let i = 0; i < locations.length; i++) {
                    for (let j = i + 1; j < locations.length; j++) {
                        const distance = calculateDistance(locations[i].latlng, locations[j].latlng);
                        if (distance > 33) {
                            isValid = false;
                            alert(`유저 간의 거리가 멀어요!`);
                            break;
                        }
                    }
                    if (!isValid) break;
                }
                if (isValid) {
                    const midpoint = calculateGreatCircleMidpoint(locations);
                    clearMarkers();
                    locations.forEach(location => addMarker(location.latlng, location.name, false, false, true));
                    addMarker(midpoint, '중간지점', true);
                    map.setCenter(midpoint);
                    recommendPlaces(midpoint);

                    // 음식점과 카페 버튼 추가
                    addCategoryButtons();

                    document.querySelector('main').classList.remove('fullscreen');
                }
            }
        })
        .catch(alert);
}



function addMarker(position, title, isMidpoint = false, isRecommendedPlace = false, isUser = false) {
    let markerImage = null;
    if (isMidpoint) {
        markerImage = new kakao.maps.MarkerImage(midpointMarkerIcon, new kakao.maps.Size(24, 35));
    } else if (isRecommendedPlace) {
        markerImage = new kakao.maps.MarkerImage(starMarkerIcon, new kakao.maps.Size(24, 35));
    }

    const marker = new kakao.maps.Marker({ map, position, title, image: markerImage });
    if (isUser) {
        const infowindow = new kakao.maps.InfoWindow({ content: `<div style="padding:5px;white-space:nowrap;">${title}</div>` });
        infowindow.open(map, marker);
    }

    markers.push(marker);
    return marker;
}

function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
    placeMarkers.forEach(marker => marker.setMap(null));
    placeMarkers = [];
    const placeList = document.getElementById('place-list');
    if (placeList) {
        placeList.innerHTML = '';
    }
}