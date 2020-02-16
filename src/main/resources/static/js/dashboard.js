const addDomainButton = document.querySelector('.sidebar-add');
const addDomainPopup = document.querySelector('.add-domain-popup');

const addDomainPopupCancel = document.querySelector('.add-domain-popup-form-controls-input--cancel');

addDomainButton.addEventListener('click', () => {
    addDomainPopup.classList.add('active');
});

addDomainPopupCancel.addEventListener('click', (e) => {
    e.preventDefault();
    addDomainPopup.classList.remove('active');
});