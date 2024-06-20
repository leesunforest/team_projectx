function openGroupModal() {
    document.getElementById('group-modal').style.display = 'flex';
}

function closeGroupModal() {
    document.getElementById('group-modal').style.display = 'none';
}

function openAddressSearchModal(input) {
    const modal = document.getElementById('address-search-modal');
    modal.dataset.inputId = input.id;
    modal.style.display = 'flex';
    document.getElementById('address-search-input').value = '';
    document.getElementById('address-search-results').innerHTML = '';
}

function closeAddressSearchModal() {
    document.getElementById('address-search-modal').style.display = 'none';
}
