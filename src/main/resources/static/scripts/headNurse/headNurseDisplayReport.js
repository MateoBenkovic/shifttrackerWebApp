document.addEventListener("DOMContentLoaded", function () {
    const orgUnitForm = document.getElementById("orgUnitForm");
    const employeeForm = document.getElementById("employeeForm");
    const toggleOrgUnitForm = document.getElementById("toggleOrgUnitForm");
    const toggleEmployeeForm = document.getElementById("toggleEmployeeForm");

    toggleOrgUnitForm.addEventListener("click", function () {
            orgUnitForm.classList.remove("d-none");
            employeeForm.classList.add("d-none");
        });

        toggleEmployeeForm.addEventListener("click", function () {
            employeeForm.classList.remove("d-none");
            orgUnitForm.classList.add("d-none");
        });

    // Filtriranje zaposlenika prema pretrazi
    employeeSearch.addEventListener("input", function () {
        let searchValue = employeeSearch.value.toLowerCase();
        document.querySelectorAll(".employee-checkbox").forEach(checkbox => {
            let label = checkbox.nextElementSibling;
            if (label.textContent.toLowerCase().includes(searchValue)) {
                checkbox.parentElement.style.display = "block";
            } else {
                checkbox.parentElement.style.display = "none";
            }
        });
    });

    employeeForm.addEventListener("submit", function (event) {
            const checkboxes = document.querySelectorAll(".employee-checkbox");
            const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

            if (!isChecked) {
                event.preventDefault(); // Sprječava slanje forme
                alert("Morate odabrati barem jednog zaposlenika!");
            }
        });
});