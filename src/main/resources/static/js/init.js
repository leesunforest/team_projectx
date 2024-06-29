document.addEventListener('DOMContentLoaded', () => {
    initializeMap(() => {
        setupEventListeners();
        document.querySelector('main').classList.add('fullscreen');
    });

    document.getElementById('address-search-button').addEventListener('click', () => {
        executeAddressSearch();
    });

    document.getElementById('address-search-input').addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent form submission if inside a form
            executeAddressSearch();
        }
    });
});

function setupEventListeners() {
    document.getElementById('groupButton').addEventListener('click', openGroupModal);
    document.querySelector('.close').addEventListener('click', closeGroupModal);
    document.querySelector('.close-address-modal').addEventListener('click', closeAddressSearchModal);
    document.getElementById('addGroup').addEventListener('click', addGroupInput);
    document.getElementById('findMidpoint').addEventListener('click', () => {
        closeGroupModal();
        findMidpoint();
        document.querySelector('main').classList.remove('fullscreen');
    });
    document.querySelectorAll('.category').forEach(button => {
        button.addEventListener('click', () => {
            toggleFilterPlaces(button);
            searchAndDisplayPlaces();  // 중간지점 찾기 전에도 장소 검색 실행
        });
    });
    document.addEventListener('keydown', (event) => {
        if (event.key === 'Escape') closeAddressSearchModal();
    });
}