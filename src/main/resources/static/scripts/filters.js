document.getElementById("applyFilter").addEventListener("click", function() {
    const selectedIspostava = document.querySelector('input[name="ispostava"]:checked')?.value || "";
    const selectedUloga = document.querySelector('input[name="uloga"]:checked')?.value || "";

    const rows = document.querySelectorAll("table tbody tr");

    rows.forEach(row => {
        const uloga = row.cells[3].textContent.trim(); // Uloga (4. stupac, index 3)
        const radnaJedinica = row.cells[1].textContent.trim(); // Radna jedinica (2. stupac, index 1)

        if ((selectedIspostava === "" || radnaJedinica === selectedIspostava) &&
            (selectedUloga === "" || uloga === selectedUloga)) {
            row.style.display = ""; // Prikazati red ako odgovara filterima
        } else {
            row.style.display = "none"; // Sakriti red ako ne odgovara
        }
    });
});

document.getElementById("clearFilter").addEventListener("click", function() {
    document.querySelectorAll('input[name="ispostava"]:checked, input[name="uloga"]:checked')
        .forEach(input => input.checked = false); // Poništi radio button odabire

    document.querySelectorAll("table tbody tr").forEach(row => {
        row.style.display = ""; // Prikazati sve redove
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchInput");

    searchInput.addEventListener("input", function () {
        const searchValue = searchInput.value.toLowerCase().trim();
        const rows = document.querySelectorAll("table tbody tr");

        rows.forEach(row => {
            const imePrezimeCell = row.cells[0]; // 1. stupac (Ime i prezime)
            if (imePrezimeCell) {
                const imePrezime = imePrezimeCell.textContent.toLowerCase().trim();
                if (imePrezime.includes(searchValue)) {
                    row.style.display = ""; // Prikaži red ako odgovara pretrazi
                } else {
                    row.style.display = "none"; // Sakrij red ako ne odgovara
                }
            }
        });
    });
});