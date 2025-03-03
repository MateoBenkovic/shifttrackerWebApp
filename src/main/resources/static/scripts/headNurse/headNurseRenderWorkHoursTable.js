export function updateTable(workHours, workingTimes, year, month, schedule) {
    const tableHead = document.querySelector("#workHoursTable thead");
    const tableBody = document.querySelector("#workHoursTable tbody");
    console.log(workingTimes);
    console.log(workHours);

    tableHead.innerHTML = "";
    tableBody.innerHTML = "";

    const daysInMonth = getDaysInMonth(year, month);

    const holidays = ["2025-01-01", "2025-01-06", "2025-04-20", "2025-04-21", "2025-05-01", "2025-05-30",
                      "2025-06-19", "2025-06-22", "2025-08-05", "2025-08-15", "2025-11-01", "2025-11-18",
                      "2025-12-25", "2025-12-26"];

    // Kreiranje header reda
    let headerHTML = `<tr><th id="work-type">Vrsta rada</th>`;
    for (let i = 1; i <= daysInMonth; i++) {
        const dateString = `${year}-${String(month).padStart(2, '0')}-${String(i).padStart(2, '0')}`;
        const workEntry = workingTimes[dateString] || [];
        workEntry.sort((a, b) => a.startHour - b.startHour);

        let firstShiftDataAttr = "";
        if (workEntry.length > 0) {
            firstShiftDataAttr = `data-shift='${JSON.stringify([workEntry[0]])}'`;
            console.log("Prva: " + JSON.stringify([workEntry[0]]));
        }

        headerHTML += `<th data-day ${firstShiftDataAttr} onclick="openForm(${i}, this, ${year}, ${month})" class="calendar-day">${i}</th>`;
    }

    headerHTML += `<th id="work-type" style="text-align: center !important; font-weight: bold;">Ukupno</th></tr>`;
    tableHead.innerHTML = headerHTML;

    // Redovi za početak i zavrpetak rada prema rasporedu
    let scheduleStart = `<tr style="border-top: 2px solid black;"><td id="work-type">Početak rada prema rasporedu</td>`;
    let scheduleEnd = `<tr style="border-bottom: 2px solid black;"><td id="work-type" >Završetak rada prema rasporedu</td>`;

    // Redovi za početak rada, završetak rada i ukupno radno vrijeme
    let startRow = `<tr><td id="work-type">Početak rada</td>`;
    let endRow = `<tr><td id="work-type">Završetak rada</td>`;
    let startRow2 = `<tr><td id="work-type">Početak rada 2</td>`;
    let endRow2 = `<tr><td id="work-type">Završetak rada 2</td>`;
    let totalRow = `<tr style="border-top: 2px solid black; border-bottom: 2px solid black; font-weight: bold;"><td id="work-type">UKUPNO RADNO VRIJEME</td>`;
    let totalMonthWorkHours = 0;

    for (let i = 1; i <= daysInMonth; i++) {
        let dateKey = `${year}-${month.toString().padStart(2, "0")}-${i.toString().padStart(2, "0")}`;
        let workTimes = workingTimes[dateKey] || [];

        let totalHours = 0;

        workTimes.sort((a, b) => a.startHour - b.startHour);

        let firstShift = workTimes.length > 0 ? workTimes[0] : null;
        let secondShift = workTimes.length > 1 ? workTimes[1] : null;

        if (firstShift) totalHours += firstShift.totalHours;
        if (secondShift) totalHours += secondShift.totalHours;

        totalMonthWorkHours += totalHours;

        // Check if it's Saturday or Sunday
        const date = new Date(dateKey);
        const isWeekend = (date.getDay() === 0 || date.getDay() === 6); // Sunday = 0, Saturday = 6
        const isHoliday = holidays.includes(dateKey); // Check if the date is a holiday

        // Add styles for weekends and holidays
        const dayClass = isHoliday ? 'holiday' : isWeekend ? 'weekend' : '';

        let hoursFromEntry = schedule.find(entry => entry.category === "hours_from");
        let hoursToEntry = schedule.find(entry => entry.category === "hours_to");

        let hoursFrom = hoursFromEntry ? hoursFromEntry[`day${i.toString().padStart(2, "0")}`] : null;
        let hoursTo = hoursToEntry ? hoursToEntry[`day${i.toString().padStart(2, "0")}`] : null;

        scheduleStart += `<td class="${dayClass}">${hoursFrom !== null ? hoursFrom.toString().padStart(2, "0") : ""}</td>`;
        scheduleEnd += `<td class="${dayClass}">${hoursTo !== null ? hoursTo.toString().padStart(2, "0") : ""}</td>`;

        let deviationIdStart = (firstShift && (hoursFrom === null || firstShift.startHour.toString().padStart(2, '0') !== hoursFrom.toString().padStart(2, "0"))) ? 'id="deviation"' : '';
        let deviationIdEnd = (firstShift && (hoursTo === null || firstShift.endHour.toString().padStart(2, "0") !== hoursTo.toString().padStart(2, "0"))) ? 'id="deviation"' : '';
        let deviationIdStart2 = (secondShift && (hoursFrom === null || secondShift.startHour.toString().padStart(2, "0") !== hoursFrom.toString().padStart(2, "0"))) ? 'id="deviation"' : '';
        let deviationIdEnd2 = (secondShift && (hoursTo === null || secondShift.endHour.toString().padStart(2, "0") !== hoursTo.toString().padStart(2, "0"))) ? 'id="deviation"' : '';


        if (firstShift) {
            startRow += `<td class="${dayClass}" ${deviationIdStart}>${firstShift.startHour.toString().padStart(2, '0')}</td>`;
            endRow += `<td class="${dayClass}" ${deviationIdEnd}>${firstShift.endHour.toString().padStart(2, '0')}</td>`;
        } else {
            startRow += `<td class="${dayClass}"></td>`;
            endRow += `<td class="${dayClass}"></td>`;
        }

        let secondShiftDataAttr = secondShift ? `data-shift='${JSON.stringify([secondShift])}'` : '';

        if (secondShift) {
            startRow2 += `<td class="${dayClass} secondShift" ${deviationIdStart2} ${secondShiftDataAttr} onclick="openForm(${i}, this, ${year}, ${month})" style="cursor: pointer; hover: background: rgba(255, 193, 7, 0.8) !important;">${secondShift.startHour.toString().padStart(2, '0')}</td>`;
            endRow2 += `<td class="${dayClass} secondShift" ${deviationIdEnd2} ${secondShiftDataAttr} onclick="openForm(${i}, this, ${year}, ${month})" style="cursor: pointer; hover: background: rgba(255, 193, 7, 0.8) !important;">${secondShift.endHour.toString().padStart(2, '0')}</td>`;
        } else {
            if (firstShift && firstShift.endHour.toString().padStart(2, "0") < 24) {
                startRow2 += `<td class="${dayClass} secondShift" onclick="openForm(${i}, this, ${year}, ${month})" style="cursor: pointer; hover: background: rgba(255, 193, 7, 0.8) !important;"></td>`;
                endRow2 += `<td class="${dayClass} secondShift" onclick="openForm(${i}, this, ${year}, ${month})" style="cursor: pointer; hover: background: rgba(255, 193, 7, 0.8) !important;"></td>`;
            } else {
                startRow2 += `<td class="${dayClass}"></td>`;
                endRow2 += `<td class="${dayClass}"></td>`;
            }

        }

        totalRow += `<td class="${dayClass}">${totalHours > 0 ? totalHours : ""}</td>`;
    }


    scheduleStart += `<td></td></tr>`;
    scheduleEnd += `<td></td></tr>`;
    startRow += `<td></td></tr>`;
    endRow += `<td></td></tr>`;
    startRow2 += `<td></td></tr>`;
    endRow2 += `<td></td></tr>`;
    totalRow += `<td>${totalMonthWorkHours}</td></tr>`;

    tableBody.innerHTML += scheduleStart + scheduleEnd + startRow + endRow + startRow2 + endRow2 + totalRow;

    // Dodavanje radnih sati iz workHours
    workHours.forEach(row => {
        let tr = `<tr><td style="text-align: left;">${row.idWorkTypes} - ${row.workTypeName}`;
        if (row.copNum !== null && row.copNum !== undefined && row.copNum !== '') {
            tr += ` - ${row.copNum}</td>`;
        }

        for (let i = 1; i <= daysInMonth; i++) {
            let dayKey = `day${String(i).padStart(2, '0')}`;
            const dateKey = `${year}-${month.toString().padStart(2, "0")}-${i.toString().padStart(2, "0")}`;

            // Check if it's Saturday or Sunday
            const date = new Date(dateKey);
            const isWeekend = (date.getDay() === 0 || date.getDay() === 6); // Sunday = 0, Saturday = 6
            const isHoliday = holidays.includes(dateKey); // Check if the date is a holiday

            // Add styles for weekends and holidays
            const dayClass = isWeekend ? 'weekend' : isHoliday ? 'holiday' : '';

            let value = row[dayKey];

                    if (value !== null) {
                        let numValue = parseFloat(value);

                        // Provjeri je li decimalni broj i je li u formatu X.5
                        if (!Number.isInteger(numValue) && numValue % 1 !== 0.5) {
                            numValue = Math.round(numValue); // Zaokruži na cijeli broj
                        }
                        value = numValue; // Postavi novu vrijednost
                    } else {
                        value = "";
                    }

            tr += `<td class="${dayClass}">${value}</td>`;
        }

        tr += `<td>${row.total}</td></tr>`;
        tableBody.innerHTML += tr;
    });
}

// Funkcija za dohvacanje broja dana u mjesecu
function getDaysInMonth(year, month) {
    return new Date(year, month, 0).getDate();
}

