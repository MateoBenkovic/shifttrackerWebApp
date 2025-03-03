import { updateTable } from "./headNurseRenderWorkHoursTable.js";

window.onload = function () {
    const monthPicker = document.getElementById("monthYearSelection");

    // Dohvati trenutni mjesec i godinu
    const today = new Date();
    const currentYear = today.getFullYear();
    const currentMonth = String(today.getMonth() + 1).padStart(2, "0"); // Dodaje nulu ako je potrebno (npr. 01, 02...)

    // Postavi početnu vrijednost inputa
    monthPicker.value = `${currentYear}-${currentMonth}`;

    // Pozovi funkciju za učitavanje podataka odmah
    loadWorkHours(currentYear, currentMonth);
};

document.getElementById("monthYearSelection").addEventListener("change", function () {
    const selectedDate = this.value;
    const [year, month] = selectedDate.split("-").map(Number);

    loadWorkHours(year, month);
});

const employeeId = document.getElementById("employeeId").value;

function loadWorkHours(year, month) {
    fetch(`/head_nurse/employee/workhour/data?year=${year}&month=${month}&id=${employeeId}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Greška: ${response.status} ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        updateTable(data.workHours, data.workingTimes, year, month, data.schedule);
        const prescribedHours = data.period;
        document.getElementById('prescribed-hours').textContent = `${prescribedHours.totalHours}`;
    })
    .catch(error => console.error("Greška:", error));
}

function confirmDelete(shiftId, startDate) {
    let employeeId = document.getElementById("employeeId").value;
    let formattedDate = new Date(startDate).toLocaleDateString("hr-HR");
    let confirmAction = confirm(`Jeste li sigurni da želite obrisati radno vrijeme za dan: ${formattedDate}?`);

    if (confirmAction) {
        window.location.href = "/head_nurse/employee/workhour/delete?workingTimeToDelete=" + shiftId + "&employeeId=" + employeeId;
    }
}

window.confirmDelete = confirmDelete;