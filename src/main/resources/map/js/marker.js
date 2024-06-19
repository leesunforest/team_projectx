// marker.js
import { map } from './map.js';

export let markers = [];

const midpointMarkerIcon = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png'; // 중간지점 마커 이미지 URL
const starMarkerIcon = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'; // 추천 장소 마커 이미지 URL

export function addMarker(position, title, isMidpoint = false, isRecommendedPlace = false, isUser = false, category = null) {
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

    marker.isUser = isUser;
    marker.isMidpoint = isMidpoint;
    marker.category = category; // 마커에 카테고리 속성 추가
    markers.push(marker);
    return marker; // Return the marker to add click event later
}

export function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}
