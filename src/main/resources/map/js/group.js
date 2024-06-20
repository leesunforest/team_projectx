function addGroupInput() {
    const groupInputs = document.getElementById('groupInputs');
    const index = groupInputs.getElementsByClassName('group-input').length + 1;
    const newInput = document.createElement('div');
    newInput.className = 'group-input';
    newInput.innerHTML = `
        <label for="group${index}">유저 ${index}:</label>
        <input type="text" id="group${index}" class="group" placeholder="주소 또는 키워드 입력" name="group${index}" readonly onclick="openAddressSearchModal(this)">
        <div id="keywordList${index}" class="keyword-list"></div>
        <button class="remove-button" onclick="removeGroupInput(this)">X</button>
    `;
    groupInputs.appendChild(newInput);
    ensureMinimumOneUser();
}

function removeGroupInput(button) {
    button.parentElement.remove();
    renumberUsers();
    ensureMinimumOneUser();
}

function renumberUsers() {
    const groupInputs = document.getElementById('groupInputs').getElementsByClassName('group-input');
    Array.from(groupInputs).forEach((input, index) => {
        const label = input.querySelector('label');
        const inputField = input.querySelector('input');
        label.setAttribute('for', `group${index + 1}`);
        label.textContent = `유저 ${index + 1}:`;
        inputField.id = `group${index + 1}`;
        inputField.name = `group${index + 1}`;
        inputField.setAttribute('onclick', `openAddressSearchModal(this)`);
    });
}

function ensureMinimumOneUser() {
    if (document.getElementById('groupInputs').getElementsByClassName('group-input').length === 0) {
        addGroupInput();
    }
}
