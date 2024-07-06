let lastClickedMarker = null;

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
                    const marker = addMarker(latlng, place.place_name, false, true, keyword);
                    marker.category = keyword;
                    placeMarkers.push(marker);

                    // 각 장소에 대한 목록을 생성하고 HTML 구조 설정, save-btn 버튼 추가
                    const li = document.createElement('li');
                    li.className = 'place-item';
                    li.innerHTML = `
                        <div class="place-name">
                            ${place.place_name}
                            <button class="save-btn" data-saved="false">Save</button>
                        </div>
                        <div class="place-info" style="display: none;">
                            <p>주소: ${place.road_address_name || place.address_name}</p>
                            <p>전화번호: ${place.phone || '정보 없음'}</p>
                            <p><a href="${place.place_url}" target="_blank">카카오맵에서 보기</a></p>
                        </div>
                    `;

                    // 장소 정보(카테고리, 좌표, placeId)를 데이터 속성으로 저장, 생성된 목록 항목을 placeList에 추가
                    li.dataset.category = keyword;
                    li.dataset.latlng = JSON.stringify(latlng);
                    li.dataset.placeId = place.id; // placeId 저장
                    placeList.appendChild(li);

                    // clickHandler : 목록 항목 OR 마커 클릭 시 장소 정보를 표시하고 지도의 중심을 해당 위치로 이동
                    const clickHandler = () => {
                        closeAllPlaceInfos();
                        displayPlaceInfo(li, place);
                        map.panTo(latlng);
                        focusOnListItem(li);
                        fetchPlaceDetails(place.id, li); // 가게 상세 정보 불러오기

                        // 이전에 클릭된 마커가 있으면 원래 이미지로 되돌림
                        if (lastClickedMarker && lastClickedMarker instanceof kakao.maps.Marker) {
                            lastClickedMarker.setImage(lastClickedMarker.normalIcon);
                        }
                        // 클릭된 마커 이미지 확대
                        if (keyword === 'FD6') {
                            marker.setImage(new kakao.maps.MarkerImage('/images/enlarged-restaurantmarker.png', new kakao.maps.Size(48, 70)));
                        } else if (keyword === 'CE7') {
                            marker.setImage(new kakao.maps.MarkerImage('/images/enlarged-cafemarker.png', new kakao.maps.Size(48, 70)));
                        }

                        // 현재 클릭된 마커를 lastClickedMarker로 설정
                        lastClickedMarker = marker;
                    };

                    kakao.maps.event.addListener(marker, 'click', clickHandler);
                    li.querySelector('.place-name').addEventListener('click', clickHandler);

                    // 저장 버튼을 클릭시 save와 Unsave 상태를 토글하고, 콘솔에 메시지 출력
                    const saveBtn = li.querySelector('.save-btn');
                    saveBtn.addEventListener('click', () => {
                        const saved = saveBtn.dataset.saved === 'true';
                        saveBtn.dataset.saved = !saved;
                        saveBtn.textContent = !saved ? 'Unsave' : 'Save';

                        // 서버에서 주입된 userId 가져오기

                        let userId = document.getElementById('userId').value;
                        console.log("recommend userId", userId);

                        if (!userId) {
                            // userId가 없으면 로그인 페이지로 이동
                            window.location.href = '/login';
                            return;
                        }

                        // userId가 있는 경우에만 저장 또는 삭제 처리를 진행
                        if (!saved) {
                            // Save the place (add to the list, database, etc.)
                            const placeInfo = {
                                userId: userId,
                                storeName: place.place_name,
                                storeAddress: place.road_address_name || place.address_name,
                                storeNumber: place.phone || ''
                            };

                            // 가게 저장 POST
                            fetch('/mypage/favorites/save', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify(placeInfo)
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        return response.text().then(text => { throw new Error(text) });
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                    console.log(`Saved: ${place.place_name}`);
                                    saveBtn.dataset.favoriteId = data.favoriteId; // 저장된 favoriteId 를 버튼에 저장
                                })
                                .catch(error => {
                                    console.error('Error saving favorite:', error);
                                });
                        } else {
                            // 가게 삭제 DELETE
                            const favoriteId = saveBtn.dataset.favoriteId; // 저장된 favoriteId 사용
                            if (favoriteId) {
                                fetch(`/mypage/favorites/delete/${favoriteId}`, {
                                    method: 'DELETE'
                                })
                                    .then(response => {
                                        if (!response.ok) {
                                            return response.text().then(text => { throw new Error(text) });
                                        }
                                        console.log(`Unsaved: ${place.place_name}`);
                                    })
                                    .catch(error => {
                                        console.error('저장 중에 문제가 발생했습니다. : ', error);
                                    });
                            } else {
                                console.error('favoriteId 를 찾을 수 없습니다.');
                            }
                        }
                    });
                });
            }
        }, { location: midpoint, radius: 500 });
    });
}

/* function recommendPlaces(midpoint) 원형

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
                    const marker = addMarker(latlng, place.place_name, false, true, keyword);
                    marker.category = keyword;
                    placeMarkers.push(marker);

                    // 각 장소에 대한 목록을 생성하고 HTML 구조 설정, save-btn 버튼 추가
                    const li = document.createElement('li');
                    li.className = 'place-item';
                    li.innerHTML = `
                        <div class="place-name">
                            ${place.place_name}
                            <button class="save-btn" data-saved="false">Save</button>
                        </div>
                        <div class="place-info" style="display: none;">
                            <p>주소: ${place.road_address_name || place.address_name}</p>
                            <p>전화번호: ${place.phone || '정보 없음'}</p>
                            <p><a href="${place.place_url}" target="_blank">카카오맵에서 보기</a></p>
                        </div>
                    `;

                    // 장소 정보(카테고리, 좌표, placeId)를 데이터 속성으로 저장, 생성된 목록 항목을 placeList에 추가
                    li.dataset.category = keyword;
                    li.dataset.latlng = JSON.stringify(latlng);
                    li.dataset.placeId = place.id; // placeId 저장
                    placeList.appendChild(li);

                    // clickHandler : 목록 항목 OR 마커 클릭 시 장소 정보를 표시하고 지도의 중심을 해당 위치로 이동
                    const clickHandler = () => {
                        closeAllPlaceInfos();
                        displayPlaceInfo(li, place);
                        map.panTo(latlng);
                        focusOnListItem(li);
                        fetchPlaceDetails(place.id, li); // 가게 상세 정보 불러오기

                        // 이전에 클릭된 마커가 있으면 원래 이미지로 되돌림
                        if (lastClickedMarker && lastClickedMarker instanceof kakao.maps.Marker) {
                            lastClickedMarker.setImage(lastClickedMarker.normalIcon);
                        }
                        // 클릭된 마커 이미지 확대
                        if (keyword === 'FD6') {
                            marker.setImage(new kakao.maps.MarkerImage('/images/enlarged-restaurantmarker.png', new kakao.maps.Size(48, 70)));
                        } else if (keyword === 'CE7') {
                            marker.setImage(new kakao.maps.MarkerImage('/images/enlarged-cafemarker.png', new kakao.maps.Size(48, 70)));
                        }

                        // 현재 클릭된 마커를 lastClickedMarker로 설정
                        lastClickedMarker = marker;
                    };

                    kakao.maps.event.addListener(marker, 'click', clickHandler);
                    li.querySelector('.place-name').addEventListener('click', clickHandler);

                    // 저장 버튼을 클릭시 save와 Unsave 상태를 토글하고, 콘솔에 메시지 출력
                    const saveBtn = li.querySelector('.save-btn');
                    saveBtn.addEventListener('click', () => {
                        const saved = saveBtn.dataset.saved === 'true';
                        saveBtn.dataset.saved = !saved;
                        saveBtn.textContent = !saved ? 'Unsave' : 'Save';
                        if (!saved) {
                            // Save the place (add to the list, database, etc.)
                            const placeInfo = {
                                userId: 'test1', // 실제 사용자 아이디 사용해야 함. userId: userId 로 받도록 하기!!
                                storeName: place.place_name,
                                storeAddress: place.road_address_name || place.address_name,
                                storeNumber: place.phone || ''
                            };

                            // 가게 저장 POST
                            fetch('/mypage/favorites/save', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                body: JSON.stringify(placeInfo)
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        return response.text().then(text => { throw new Error(text) });
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                    console.log(`Saved: ${place.place_name}`);
                                    saveBtn.dataset.favoriteId = data.favoriteId; // 저장된 favoriteId 를 버튼에 저장
                                })
                                .catch(error => {
                                    console.error('Error saving favorite:', error);
                                });
                        } else {
                            // 가게 삭제 DELETE
                            const favoriteId = saveBtn.dataset.favoriteId; // 저장된 favoriteId 사용
                            if (favoriteId) {
                                fetch(`/mypage/favorites/delete/${favoriteId}`, {
                                    method: 'DELETE'
                                })
                                    .then(response => {
                                        if (!response.ok) {
                                            return response.text().then(text => { throw new Error(text) });
                                        }
                                        console.log(`Unsaved: ${place.place_name}`);
                                    })
                                    .catch(error => {
                                        console.error('Error unsaving favorite:', error);
                                    });
                            } else {
                                console.error('favoriteId 를 찾을 수 없습니다.');
                            }
                        }
                    });
                });
            }
        }, { location: midpoint, radius: 500 });
    });
}
 */

function fetchPlaceDetails(placeId, listItem) {
    const ps = new kakao.maps.services.Places();
    ps.keywordSearch(placeId, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
            const place = data[0];
            const infoDiv = listItem.querySelector('.place-info');
            infoDiv.innerHTML = `
                <p>주소: ${place.road_address_name || place.address_name}</p>
                <p>전화번호: ${place.phone || '정보 없음'}</p>
                <p>카테고리: ${place.category_name}</p>
                <p>영업시간: ${place.opening_hours || '정보 없음'}</p>
                <p>리뷰 수: ${place.user_ratings_total || '정보 없음'}</p>
                <p>평점: ${place.rating || '정보 없음'}</p>
            `;
            infoDiv.style.display = 'block';
        }
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