import { openForm } from './employeeWorkHoursFormDisplay.js';

// Pronađi input element
const monthInput = document.querySelector("input[type='month']");

// Postavi početnu vrijednost na trenutni mjesec i godinu
const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();
const formattedMonth = `${currentYear}-${currentMonth.toString().padStart(2, '0')}`;
monthInput.value = formattedMonth;

// Generiraj kalendar za inicijalno odabrani mjesec i godinu
generateCalendar(currentYear, currentMonth);

// Dodaj event listener za promjenu vrijednosti inputa
monthInput.addEventListener("change", function() {
    const [year, month] = this.value.split("-").map(Number);
    generateCalendar(year, month);
});

function generateCalendar(year, month) {
    const calendarRow = document.getElementById("calendarRow");
    calendarRow.innerHTML = ""; // Očisti tablicu prije generiranja

    const daysInMonth = new Date(year, month, 0).getDate(); // Broj dana u mjesecu

    // Dodaj prvi stupac "Vrsta rada"
    const typeTh = document.createElement("th");
    typeTh.textContent = "Vrsta rada";
    typeTh.style.cursor = "default";
    calendarRow.appendChild(typeTh);

    // Generiraj dane
    for (let day = 1; day <= daysInMonth; day++) {
        const th = document.createElement("th");
        th.textContent = day;
        th.classList.add("calendar-day");
        th.onclick = function() { openForm(day, this, year, month); }; // Zadrži funkcionalnost otvaranja forme
        calendarRow.appendChild(th);
    }

    // Dodaj zadnji stupac "Ukupno"
    const totalTh = document.createElement("th");
    totalTh.textContent = "Ukupno";
    totalTh.style.cursor = "default";
    calendarRow.appendChild(totalTh);
}

//Ograničenja odabira godine
const monthYearSelection = document.getElementById("monthYearSelection");

const minYear = currentYear - 1;
const maxYear = currentYear;

// Postavi ograničenja
monthYearSelection.min = `${minYear}-01`;
monthYearSelection.max = `${maxYear}-12`;

