function searchAddress(input) {
    const keywordList = document.getElementById('address-search-results');
    keywordList.innerHTML = '';

    const ps = new kakao.maps.services.Places();
    const keyword = input.value.trim();
    if (!keyword) return;

    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            keywordList.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.place_name}</strong><br><small id="address-${item.id}">Loading address...</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    const modal = document.getElementById('address-search-modal');
                    const originalInput = document.getElementById(modal.dataset.inputId);
                    originalInput.value = item.place_name;
                    originalInput.dataset.fullAddress = '';
                    keywordList.innerHTML = '';
                    closeAddressSearchModal();
                    getFullAddress(item, `address-${item.id}`, originalInput);
                };
                keywordList.appendChild(div);
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
            keywordList.innerHTML = '';
            result.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.address_name}</strong><br><small>${item.address_name}</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    input.value = item.address_name;
                    input.dataset.fullAddress = item.address_name;
                    keywordList.innerHTML = '';
                };
                keywordList.appendChild(div);
            });
        } else {
            keywordList.innerHTML = '<div class="autocomplete-item">No results found</div>';
        }
    });
}

function searchAddrFromKeyword(input, index) {
    const keywordList = document.getElementById(`keywordList${index}`);
    keywordList.innerHTML = '';

    const ps = new kakao.maps.services.Places();
    const keyword = input.value.trim();
    if (!keyword) return;

    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK && data.length > 0) {
            keywordList.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.place_name}</strong><br><small id="keyword-address-${index}-${item.id}">Loading address...</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    input.value = item.place_name;
                    input.dataset.fullAddress = '';
                    keywordList.innerHTML = '';
                    getFullAddress(item, `keyword-address-${index}-${item.id}`, input);
                };
                keywordList.appendChild(div);
                getFullAddress(item, `keyword-address-${index}-${item.id}`);
            });
        } else {
            performAddressSearch(keyword, keywordList, input);
        }
    }, { bounds: map.getBounds() });
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
            keywordList.innerHTML = '';
            result.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `<strong>${item.address_name}</strong><br><small>${item.address_name}</small>`;
                div.className = 'autocomplete-item';
                div.onclick = () => {
                    input.value = item.address_name;
                    input.dataset.fullAddress = item.address_name;
                    keywordList.innerHTML = '';
                };
                keywordList.appendChild(div);
            });
        } else {
            keywordList.innerHTML = '<div class="autocomplete-item">No results found</div>';
        }
    });
}
