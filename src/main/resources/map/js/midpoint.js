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
