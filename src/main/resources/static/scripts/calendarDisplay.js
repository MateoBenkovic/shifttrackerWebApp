import { openForm } from './employeeWorkHoursFormDisplay.js';

  document.getElementById("monthSelect").addEventListener("change", function() {
    generateCalendar(parseInt(this.value));
});

function generateCalendar(selectedMonth) {
    const calendarRow = document.getElementById("calendarRow");
    calendarRow.innerHTML = ""; // Očisti tablicu prije generiranja


    const currentYear = new Date().getFullYear(); // Trenutna godina
    const daysInMonth = new Date(currentYear, selectedMonth, 0).getDate(); // Broj dana u mjesecu

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
        th.onclick = function() { openForm(day, this); }; // Zadrži funkcionalnost otvaranja forme
        calendarRow.appendChild(th);
    }

    // Dodaj zadnji stupac "Ukupno"
    const totalTh = document.createElement("th");
    totalTh.textContent = "Ukupno";
    totalTh.style.cursor = "default";
    calendarRow.appendChild(totalTh);
}

// Pozovi funkciju da inicijalno postavi trenutni mjesec
const today = new Date();
document.getElementById("monthSelect").value = today.getMonth() + 1; // Postavi trenutni mjesec
generateCalendar(today.getMonth() + 1);