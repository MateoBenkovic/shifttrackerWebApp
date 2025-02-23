function openForm(day, element, year, month, workDataAttr) {
    const formContainer = document.getElementById("workTimeFormContainer");
    const workDateInput = document.getElementById("workDate");
    const overlay = document.getElementById("overlay");
    const startTimeInput = document.getElementById("startShift");
    const endTimeInput = document.getElementById("endShift");
    const deleteButton = document.getElementById("deleteShiftButton");
    const errorMessage = document.getElementById("errorMessage");

    formContainer.style.display = "block";
    formContainer.style.visibility = "hidden";

    const formWidth = formContainer.offsetWidth;

    formContainer.style.display = "none";
    formContainer.style.visibility = "visible";

    startTimeInput.value = "";
    endTimeInput.value = "";

    // Postavi datum u input[type="date"]
    const selectedDate = `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
    workDateInput.value = selectedDate;

    // Dohvati podatke o smjeni ako postoje
    let existingShifts = [];
    const shiftData = element.getAttribute("data-shift");
    if (shiftData && shiftData !== "null" && shiftData !== "undefined") {
        try {
            existingShifts = JSON.parse(shiftData);
            console.log(existingShifts);
        } catch (e) {
            console.error("Greška pri parsiranju data-shift:", e);
        }
    }

    if (existingShifts.length > 0) {
        const shift = existingShifts[0]; // Uzmi prvu smjenu ako ih ima više

        // Postavi početak i kraj smjene
        const formattedStartHour = shift.startHour === 24 ? "00" : String(shift.startHour).padStart(2, "0");
        const formattedEndHour = shift.endHour === 24 ? "00" : String(shift.endHour).padStart(2, "0");

        let startDate = shift.date; // Originalni datum smjene
            let endDate = shift.date;   // Datum završetka smjene

            if (shift.endHour === 24) {
                let endDateObj = new Date(shift.date);
                endDateObj.setDate(endDateObj.getDate() + 1); // Povećaj dan za jedan
                endDate = endDateObj.toISOString().split("T")[0]; // Dobij formatiran datum "YYYY-MM-DD"
            }

        startTimeInput.value = `${startDate}T${formattedStartHour}:00`;
        endTimeInput.value = `${endDate}T${formattedEndHour}:00`;

        console.log("Dodjeljujem href za brisanje:", `/employee/workhour/delete/${shift.id}`);
        // Omogući gumb za brisanje i dodaj ID smjene
        deleteButton.style.display = "block";
        deleteButton.setAttribute("onclick", `window.location.href='/employee/workhour/delete?workingTimeToDelete=${shift.id}'`);

    } else {
        // Ako nema smjene, sakrij gumb za brisanje
        deleteButton.style.display = "none";
        deleteButton.removeAttribute("data-id");
        deleteButton.removeAttribute("onclick");
    }

    document.querySelectorAll(".highlighted-date").forEach(el => el.classList.remove("highlighted-date"));
    errorMessage.innerHTML = "";

    element.classList.add("highlighted-date");

    const rect = element.getBoundingClientRect();
    const windowWidth = window.innerWidth;

    let leftPosition;

    if (day <= 15) {
        if (rect.right + formWidth < windowWidth) {
            leftPosition = rect.right + window.scrollX + 10;
        } else {
            leftPosition = rect.left + window.scrollX - formWidth - 10;
        }
    } else {
        if (rect.left - formWidth > 0) {
            leftPosition = rect.left + window.scrollX - formWidth - 10;
        } else {
            leftPosition = rect.right + window.scrollX + 10;
        }
    }

    if (leftPosition + formWidth > windowWidth) {
        leftPosition = windowWidth - formWidth - 20;
    }
    if (leftPosition < 0) {
        leftPosition = 10;
    }

    formContainer.style.top = `${rect.bottom + window.scrollY + 5}px`;
    formContainer.style.left = `${leftPosition}px`;

    formContainer.style.display = "block";
    overlay.style.display = "block";

    overlay.onclick = function () {
        formContainer.style.display = "none";
        overlay.style.display = "none";
        element.classList.remove("highlighted-date");
    };
}