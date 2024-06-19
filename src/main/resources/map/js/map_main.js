// main.js
import { initializeMap } from './map.js';
import { openGroupModal, closeGroupModal, openAddressSearchModal, closeAddressSearchModal } from './modal.js';
import { addGroupInput, findMidpoint } from './group.js';
import { filterPlaces, searchCategory } from './search.js';

document.addEventListener('DOMContentLoaded', () => {
    initializeMap();

    document.getElementById('groupButton').addEventListener('click', openGroupModal);
    document.querySelector('.close').addEventListener('click', closeGroupModal);
    document.querySelector('.close-address-modal').addEventListener('click', closeAddressSearchModal);
    document.getElementById('addGroup').addEventListener('click', addGroupInput);
    document.getElementById('findMidpoint').addEventListener('click', () => {
        closeGroupModal(); // 그룹 생성 창 내리기
        findMidpoint();
    });

    document.querySelectorAll('.category').forEach(button => {
        button.addEventListener('click', () => {
            const category = button.dataset.category;
            if (document.getElementById('left-panel').style.display === 'block') {
                filterPlaces(category);
            } else {
                searchCategory(category);
            }
        });
    });

    document.addEventListener('keydown', (event) => {
        if (event.key === 'Escape') {
            closeAddressSearchModal();
        }
    });
});
