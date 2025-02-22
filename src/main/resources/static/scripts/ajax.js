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

// const monthYearSelection = document.getElementById('monthYearSelection');

document.getElementById("monthYearSelection").addEventListener("change", function () {
    const selectedDate = this.value;
    const [year, month] = selectedDate.split("-").map(Number);

    loadWorkHours(year, month);
});

// tu treba fetchati i period
function loadWorkHours(year, month) {
    fetch(`/employee/workhour/data?year=${year}&month=${month}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`Greška: ${response.status} ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        updateTable(data.workHours, data.workingTimes, data.schedule, year, month);
        const prescribedHours = data.period;
        document.getElementById('prescribed-hours').textContent = `${prescribedHours.totalHours}`;
    })
    .catch(error => console.error("Greška:", error));
}





//
//function updateTable(workHours, workingTimes, schedule year, month) {
//    const tableHead = document.querySelector("#workHoursTable thead");
//    const tableBody = document.querySelector("#workHoursTable tbody");
//    console.log(workingTimes);
//    console.log(workHours);
//
//    tableHead.innerHTML = "";
//    tableBody.innerHTML = "";
//
//    const daysInMonth = getDaysInMonth(year, month);
//
//    // -- added by me --
//    const holidays = ["2025-01-01", "2025-01-06", "2025-04-20", "2025-04-21", "2025-05-01", "2025-05-30",
//                      "2025-06-19", "2025-06-22", "2025-08-05", "2025-08-15", "2025-11-01", "2025-11-18",
//                      "2025-12-25", "2025-12-26"];
//
//    // Kreiranje header reda
//    let headerHTML = `<tr><th id="work-type">Vrsta rada</th>`;
//    for (let i = 1; i <= daysInMonth; i++) {
//        headerHTML += `<th data-day onclick="openForm(${i}, this, ${year}, ${month})" class="calendar-day">${i}</th>`;
//    }
//    headerHTML += `<th id="work-type" style="text-align: center !important; font-weight: bold;">Ukupno</th></tr>`;
//    tableHead.innerHTML = headerHTML;
//
//    // Redovi za početak i zavrpetak rada prema rasporedu
//    let scheduleStart = `<tr style="border-top: 2px solid black;"><td id="work-type">Početak rada prema rasporedu</td>`;
//    let scheduleEnd = `<tr style="border-bottom: 2px solid black;"><td id="work-type" >Završetak rada prema rasporedu</td>`;
//
//
//    // Redovi za početak rada, završetak rada i ukupno radno vrijeme
//    let startRow = `<tr><td id="work-type">Početak rada</td>`;
//    let endRow = `<tr><td id="work-type">Završetak rada</td>`;
//    let startRow2 = `<tr><td id="work-type">Početak rada 2</td>`;
//    let endRow2 = `<tr><td id="work-type">Završetak rada 2</td>`;
//    let totalRow = `<tr style="border-top: 2px solid black; border-bottom: 2px solid black; font-weight: bold;"><td id="work-type">UKUPNO RADNO VRIJEME</td>`;
//    let totalMonthWorkHours = 0;
//
//    for (let i = 1; i <= daysInMonth; i++) {
//        let dateKey = `${year}-${month.toString().padStart(2, "0")}-${i.toString().padStart(2, "0")}`;
//        let workTimes = workingTimes[dateKey] || [];
//
//        let totalHours = 0;
//
//        workTimes.sort((a, b) => a.startHour - b.startHour);
//
//        let firstShift = workTimes.length > 0 ? workTimes[0] : null;
//        let secondShift = workTimes.length > 1 ? workTimes[1] : null;
//
//        if (firstShift) totalHours += firstShift.totalHours;
//        if (secondShift) totalHours += secondShift.totalHours;
//
//        totalMonthWorkHours += totalHours;
//
//        // Check if it's Saturday or Sunday
//        const date = new Date(dateKey);
//        const isWeekend = (date.getDay() === 0 || date.getDay() === 6); // Sunday = 0, Saturday = 6
//        const isHoliday = holidays.includes(dateKey); // Check if the date is a holiday
//
//        // Add styles for weekends and holidays
//        const dayClass = isHoliday ? 'holiday' : isWeekend ? 'weekend' : '';
//
//        scheduleStart += `<td class="${dayClass}"></td>`;
//        scheduleEnd += `<td class="${dayClass}"></td>`;
//
//        if (firstShift) {
//            startRow += `<td class="${dayClass}">${firstShift.startHour.toString().padStart(2, '0')}</td>`;
//            endRow += `<td class="${dayClass}">${firstShift.endHour.toString().padStart(2, '0')}</td>`;
//        } else {
//            startRow += `<td class="${dayClass}"></td>`;
//            endRow += `<td class="${dayClass}"></td>`;
//        }
//
//        if (secondShift) {
//            startRow2 += `<td class="${dayClass}">${secondShift.startHour.toString().padStart(2, '0')}</td>`;
//            endRow2 += `<td class="${dayClass}">${secondShift.endHour.toString().padStart(2, '0')}</td>`;
//        } else {
//            startRow2 += `<td class="${dayClass}"></td>`;
//            endRow2 += `<td class="${dayClass}"></td>`;
//        }
//
//        totalRow += `<td class="${dayClass}">${totalHours > 0 ? totalHours : ""}</td>`;
//    }
//
//
//    scheduleStart += `<td></td></tr>`;
//    scheduleEnd += `<td></td></tr>`;
//    startRow += `<td></td></tr>`;
//    endRow += `<td></td></tr>`;
//    startRow2 += `<td></td></tr>`;
//    endRow2 += `<td></td></tr>`;
//    totalRow += `<td>${totalMonthWorkHours}</td></tr>`;
//
//    tableBody.innerHTML += scheduleStart + scheduleEnd + startRow + endRow + startRow2 + endRow2 + totalRow;
//
//    // dodavanje sati prema rasporedu
//
//    // Dodavanje radnih sati iz workHours
//    workHours.forEach(row => {
//        let tr = `<tr><td style="text-align: left;">${row.workTypeName}</td>`;
//
//        for (let i = 1; i <= daysInMonth; i++) {
//                    let dayKey = `day${String(i).padStart(2, '0')}`;
//                    const dateKey = `${year}-${month.toString().padStart(2, "0")}-${i.toString().padStart(2, "0")}`;
//
//                    // Check if it's Saturday or Sunday
//                    const date = new Date(dateKey);
//                    const isWeekend = (date.getDay() === 0 || date.getDay() === 6); // Sunday = 0, Saturday = 6
//                    const isHoliday = holidays.includes(dateKey); // Check if the date is a holiday
//
//                    // Add styles for weekends and holidays
//                    const dayClass = isWeekend ? 'weekend' : isHoliday ? 'holiday' : '';
//
//                    tr += `<td class="${dayClass}">${row[dayKey] !== null ? row[dayKey] : ""}</td>`;
//        }
//
//        tr += `<td>${row.total}</td></tr>`;
//        tableBody.innerHTML += tr;
//    });
//}

// Funkcija za dohvacanje broja dana u mjesecu
function getDaysInMonth(year, month) {
    return new Date(year, month, 0).getDate();
}