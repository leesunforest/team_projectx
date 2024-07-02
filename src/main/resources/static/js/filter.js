function toggleFilterPlaces(button) {
    currentCategory = currentCategory === button.dataset.category ? null : button.dataset.category;
    filterPlaces(currentCategory);
}

function filterPlaces(category) {
    const placeList = document.getElementById('place-list');
    Array.from(placeList.getElementsByTagName('li')).forEach(item => {
        item.style.display = !category || item.dataset.category === category ? 'list-item' : 'none';
    });

    placeMarkers.forEach(marker => {
        marker.setMap(!category || marker.category === category ? map : null);
    });
}
