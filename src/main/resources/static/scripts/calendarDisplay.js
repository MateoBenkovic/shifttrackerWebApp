// Postavi početnu vrijednost na trenutni mjesec i godinu
const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();

//Ograničenja odabira godine
const monthYearSelection = document.getElementById("monthYearSelection");

const minYear = currentYear - 1;
const maxYear = currentYear;

// Postavi ograničenja
monthYearSelection.min = `${minYear}-01`;
monthYearSelection.max = `${maxYear}-12`;

document.addEventListener("DOMContentLoaded", function () {

    const columnIndex = localStorage.getItem("highlightColumn");

    if (columnIndex !== null) {
        waitForTableToLoad(() => highlightColumn(parseInt(columnIndex)));
        localStorage.removeItem("highlightColumn"); // Brišemo nakon bojenja
    }

    // Pratimo kad se <thead> ažurira (jer se tablica generira dinamički)
    const theadObserver = new MutationObserver(() => {
        addThEventListeners();
    });

    theadObserver.observe(document.querySelector("#workHoursTable thead"), {
        childList: true,
        subtree: true
    });

    // Kada se forma šalje, spremamo index u localStorage
    document.getElementById("workTimeForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Spriječimo instant reload

        const selectedIndex = document.getElementById("selectedColumn").value;
        const startShift = new Date(document.getElementById("startShift").value);
        const endShift = new Date(document.getElementById("endShift").value);

        if (selectedIndex !== "") {
            localStorage.setItem("highlightColumn", selectedIndex);

            // Ako smjena prelazi u drugi dan, spremamo i sljedeći stupac
            if (startShift.getDate() !== endShift.getDate()) {
                localStorage.setItem("highlightNextColumn", parseInt(selectedIndex) + 1);
            }
        }

        // Delay za osiguravanje spremanja prije reload-a
        setTimeout(() => {
            this.submit();
        }, 100);
    });
});

// Funkcija za dodavanje event listenera na th elemente
function addThEventListeners() {
    let thElements = document.querySelectorAll("#workHoursTable th");

    thElements.forEach((th, index) => {
        th.addEventListener("click", function () {
            document.getElementById("selectedColumn").value = index;
        });
    });
}

// Funkcija za bojanje stupaca
function highlightColumn(index) {

    let cells = document.querySelectorAll(`#workHoursTable tbody tr td:nth-child(${index + 1})`);

    if (cells.length === 0) {
        console.warn(`Nema ćelija na indexu ${index + 1}. Čekam da se tablica učita...`);
        return;
    }

    cells.forEach(cell => {
        cell.style.backgroundColor = "#ffc107"; // Postavi boju
        cell.style.transition = "background-color 0.5s ease-in-out"; // Dodaj glatku tranziciju
        setTimeout(() => {
            cell.style.backgroundColor = ""; // Ukloni boju nakon 3 sekunde
        }, 3000);
    });

    // Provjera treba li obojati i sljedeći dan
    const nextColumnIndex = localStorage.getItem("highlightNextColumn");
    if (nextColumnIndex !== null) {
        let nextCells = document.querySelectorAll(`#workHoursTable tbody tr td:nth-child(${parseInt(nextColumnIndex) + 1})`);

        nextCells.forEach(cell => {
            cell.style.backgroundColor = "#ffc107"; // Postavi boju
            cell.style.transition = "background-color 0.5s ease-in-out"; // Dodaj glatku tranziciju
            setTimeout(() => {
                cell.style.backgroundColor = ""; // Ukloni boju nakon 3 sekunde
            }, 3000);
        });

        localStorage.removeItem("highlightNextColumn"); // Brišemo nakon bojenja
    }
}

// Funkcija koja čeka da se `tbody` generira prije bojenja
function waitForTableToLoad(callback) {
    const tbodyObserver = new MutationObserver(() => {
        if (document.querySelector("#workHoursTable tbody tr")) {
            tbodyObserver.disconnect(); // Prekidamo promatranje
            callback();
        }
    });

    tbodyObserver.observe(document.querySelector("#workHoursTable tbody"), {
        childList: true,
        subtree: true
    });
}

