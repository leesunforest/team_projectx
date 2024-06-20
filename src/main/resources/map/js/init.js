document.addEventListener('DOMContentLoaded', () => {
    initializeMap(() => {
        setupEventListeners();
        document.querySelector('main').classList.add('fullscreen');

        searchCategory('FD6');
        searchCategory('CE7');
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
        button.addEventListener('click', () => toggleFilterPlaces(button));
    });
    document.addEventListener('keydown', (event) => {
        if (event.key === 'Escape') closeAddressSearchModal();
    });
}
