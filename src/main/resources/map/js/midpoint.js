// midpoint.js
import { toRadians, toDegrees } from './utils.js';

export function calculateGreatCircleMidpoint(locations) {
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

export function recommendPlaces(midpoint) {
    const ps = new kakao.maps.services.Places();
    const keywords = ['FD6', 'CE7'];
    const placeList = document.getElementById('place-list');
    placeList.innerHTML = ''; // Clear the list before adding new places
    document.getElementById('left-panel').style.display = 'block'; // Show the place list panel

    keywords.forEach(category => {
        ps.categorySearch(category, (data, status) => {
            if (status === kakao.maps.services.Status.OK) {
                data.forEach(place => {
                    const latlng = new kakao.maps.LatLng(place.y, place.x);
                    const marker = addMarker(latlng, place.place_name, false, true, false, category);

                    // Add place to the list
                    const li = document.createElement('li');
                    li.textContent = `${place.place_name} (${category === 'FD6' ? '음식점' : '카페'})`;
                    li.dataset.category = category;
                    li.dataset.id = place.id;
                    li.dataset.name = place.place_name;
                    li.dataset.address = place.address_name;
                    li.dataset.review = place.review || '없음';

                    const infoDiv = document.createElement('div');
                    infoDiv.className = 'place-info';
                    infoDiv.style.display = 'none';
                    li.appendChild(infoDiv);

                    placeList.appendChild(li);

                    // Add click event to the marker
                    kakao.maps.event.addListener(marker, 'click', () => {
                        displayPlaceInfo(li, place);
                        map.setCenter(latlng); // Center the map on the marker
                    });

                    li.addEventListener('click', () => {
                        map.setCenter(latlng); // Center the map on the marker
                        displayPlaceInfo(li, place); // Display place info in the list
                    });
                });
            }
        }, { location: midpoint, radius: 500 });
    });
}
