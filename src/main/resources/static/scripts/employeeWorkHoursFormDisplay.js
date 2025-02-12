export function openForm(day, element) {

    const formContainer = document.getElementById("workTimeFormContainer");
    const workDateInput = document.getElementById("workDate");
    const overlay = document.getElementById("overlay");
    const startTimeInput = document.getElementById("startShift");
    const endTimeInput = document.getElementById("endShift");
    const errorMessage = document.getElementById("errorMessage");

    // Prvo forsiramo da forma dobije pravu širinu
    formContainer.style.display = "block";
    formContainer.style.visibility = "hidden"; // Ne vidi se dok ne dobijemo dimenzije

    // Sada možemo dobiti pravu širinu forme
    const formWidth = formContainer.offsetWidth;

    // Resetiraj formu dok se ne postavi ispravna pozicija
    formContainer.style.display = "none";
    formContainer.style.visibility = "visible"; // Sad se vidi kad je otvoriš

    startTimeInput.value = "";
    endTimeInput.value = "";

    // Postavi datum u input[type="date"]
    const selectedMonth = document.getElementById("monthSelect").value; // Dohvati odabrani mjesec
    const currentYear = new Date().getFullYear(); // Dohvati trenutnu godinu
    const selectedDate = `${currentYear}-${String(selectedMonth).padStart(2, "0")}-${String(day).padStart(2, "0")}`;

    workDateInput.value = selectedDate;

    // Resetiraj prethodne podatke
    document.querySelectorAll(".highlighted-date").forEach(el => el.classList.remove("highlighted-date"));
    errorMessage.innerHTML = "";

    // Istakni kliknuti datum
    element.classList.add("highlighted-date");

    // Pronađi poziciju kliknutog elementa
    const rect = element.getBoundingClientRect();
    const windowWidth = window.innerWidth;

    let leftPosition;

    // Ako je dan 1-15, probaj desno, ako nema mjesta -> lijevo
    if (day <= 15) {
        if (rect.right + formWidth < windowWidth) {
            leftPosition = rect.right + window.scrollX + 10; // Otvara desno
            console.log("Otvara se DESNO");
        } else {
            leftPosition = rect.left + window.scrollX - formWidth - 10; // Ako nema mjesta, otvori lijevo
            console.log("Nema mjesta desno, otvara se LIJEVO");
        }
    } else {
        // Ako je dan 16-31, probaj lijevo, ako nema mjesta -> desno
        if (rect.left - formWidth > 0) {
            leftPosition = rect.left + window.scrollX - formWidth - 10; // Otvara lijevo
            console.log("Otvara se LIJEVO");
        } else {
            leftPosition = rect.right + window.scrollX + 10; // Ako nema mjesta, otvori desno
            console.log("Nema mjesta lijevo, otvara se DESNO");
        }
    }

    // Sigurnosne provjere da ne pobjegne van ekrana
    if (leftPosition + formWidth > windowWidth) {
        leftPosition = windowWidth - formWidth - 20; // Spriječava izlazak iz ekrana desno
        console.log("Forma bi pobjegla desno - Korigiram!");
    }
    if (leftPosition < 0) {
        leftPosition = 10; // Spriječava izlazak iz ekrana lijevo
        console.log("Forma bi pobjegla lijevo - Korigiram!");
    }

    // Postavi poziciju forme blizu datuma
    formContainer.style.top = `${rect.bottom + window.scrollY + 5}px`;
    formContainer.style.left = `${leftPosition}px`;

    // Prikaži overlay i formu
    formContainer.style.display = "block";
    overlay.style.display = "block";

    console.log(`Forma otvorena na X: ${leftPosition}`);

    // Klik izvan forme zatvara formu i uklanja istaknuti datum
    overlay.onclick = function () {
        formContainer.style.display = "none";
        overlay.style.display = "none";
        element.classList.remove("highlighted-date");
        console.log("Forma zatvorena.");
    };
}