// modal.js
export function openGroupModal() {
    document.getElementById('group-modal').style.display = 'flex';
}

export function closeGroupModal() {
    document.getElementById('group-modal').style.display = 'none';
}

export function openAddressSearchModal(input) {
    const modal = document.getElementById('address-search-modal');
    modal.dataset.inputId = input.id;
    modal.style.display = 'flex';
    // 초기화 코드 추가
    document.getElementById('address-search-input').value = '';
    document.getElementById('address-search-results').innerHTML = '';
}

export function closeAddressSearchModal() {
    document.getElementById('address-search-modal').style.display = 'none';
}
