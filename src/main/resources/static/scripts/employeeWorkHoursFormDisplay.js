function openForm(day, element, year, month) {
    const formContainer = document.getElementById("workTimeFormContainer");
    const workDateInput = document.getElementById("workDate");
    const overlay = document.getElementById("overlay");
    const startTimeInput = document.getElementById("startShift");
    const endTimeInput = document.getElementById("endShift");
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