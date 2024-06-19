// map.js
export let map;
export let infowindow;
export let userLocation = null;

export function initializeMap() {
    infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

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
