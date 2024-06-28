let map, infowindow, userLocation = null;
let markers = [], locations = [], placeMarkers = [];
let currentCategory = null;

const midpointMarkerIcon = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
const starMarkerIcon = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';

function initializeMap(callback) {
    infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
    const mapContainer = document.getElementById('map');
    const defaultCenter = new kakao.maps.LatLng(37.5665, 126.9780);
    const mapOption = { center: defaultCenter, level: 3 };

    map = new kakao.maps.Map(mapContainer, mapOption);

    // Update the map center based on the user's current location
    updateUserLocation(callback);
}

function updateUserLocation(callback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position => {
            userLocation = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
            map.setCenter(userLocation);
            if (typeof callback === 'function') callback();
        }, () => {
            if (typeof callback === 'function') callback();
        });
    } else {
        if (typeof callback === 'function') callback();
    }
}

