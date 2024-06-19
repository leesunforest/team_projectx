// group.js
import { map, infowindow, userLocation } from './map.js';
import { addMarker, clearMarkers } from './marker.js';
import { calculateGreatCircleMidpoint, recommendPlaces } from './midpoint.js';

export function addGroupInput() {
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

export function removeGroupInput(button) {
    const groupInput = button.parentElement;
    groupInput.remove();
    renumberUsers();
    ensureMinimumOneUser();
}

export function renumberUsers() {
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

export function ensureMinimumOneUser() {
    const groupInputs = document.getElementById('groupInputs').getElementsByClassName('group-input');
    if (groupInputs.length === 0) {
        addGroupInput();
    }
}

export function findMidpoint() {
    const groupInputs = document.getElementsByClassName('group');
    if (groupInputs.length < 2) {
        alert('두 개 이상의 위치를 입력해 주세요.');
        return;
    }

    const promises = [];
    let locations = [];

    for (let i = 0; i < groupInputs.length; i++) {
        const input = groupInputs[i];
        const query = input.value;
        const fullAddress = input.dataset.fullAddress || query;
        promises.push(
            new Promise((resolve, reject) => {
                const geocoder = new kakao.maps.services.Geocoder();
                geocoder.addressSearch(fullAddress, (result, status) => {
                    if (status === kakao.maps.services.Status.OK && result.length > 0) {
                        const latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
                        locations.push({ latlng, name: `user${i + 1}` });
                        resolve();
                    } else {
                        reject(`주소를 찾을 수 없습니다: ${fullAddress}`);
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
