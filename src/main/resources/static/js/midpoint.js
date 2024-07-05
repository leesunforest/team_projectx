function calculateGreatCircleMidpoint(locations) {
    let X = 0, Y = 0, Z = 0;
    locations.forEach(location => {
        const lat = toRadians(location.latlng.getLat());
        const lon = toRadians(location.latlng.getLng());
        X += Math.cos(lat) * Math.cos(lon);
        Y += Math.cos(lat) * Math.sin(lon);
        Z += Math.sin(lat);
    });
    X /= locations.length;
    Y /= locations.length;
    Z /= locations.length;
    const Lon = Math.atan2(Y, X);
    const Hyp = Math.sqrt(X * X + Y * Y);
    const Lat = Math.atan2(Z, Hyp);
    return new kakao.maps.LatLng(toDegrees(Lat), toDegrees(Lon));
}

function toRadians(degrees) {
    return degrees * Math.PI / 180;
}

function toDegrees(radians) {
    return radians * 180 / Math.PI;
}

function calculateDistance(location1, location2) {
    const lat1 = location1.getLat();
    const lon1 = location1.getLng();
    const lat2 = location2.getLat();
    const lon2 = location2.getLng();
    const R = 6371; // Radius of the Earth in kilometers
    const dLat = toRadians(lat2 - lat1);
    const dLon = toRadians(lon2 - lon1);
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in kilometers
}

function toRadians(degrees) {
    return degrees * Math.PI / 180;
}