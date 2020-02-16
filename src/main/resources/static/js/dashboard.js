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



const searchInput = document.querySelector('.topbar-searchbar-input');

let grid = new Muuri('.grid', {
    dragEnabled: true,
    dragSortPredicate: {
        threshold: 50,
        action: 'swap',
    },
});

searchInput.addEventListener('input', () => {
    grid.filter(function (item) {
        return item.getElement().querySelector('.pass-card-domain-address-href').textContent.includes(searchInput.value);
    });

    if (searchInput.value === '') {
        grid.show();
    }
});
