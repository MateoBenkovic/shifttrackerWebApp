document.addEventListener("DOMContentLoaded", function () {
            let today = new Date().toISOString().split("T")[0];
            document.getElementById("workDate").value = today;

            const btnDnevna = document.getElementById('btnDnevna');
            const btnNocna = document.getElementById('btnNocna');
            const startTimeInput = document.getElementById('startShift');
            const endTimeInput = document.getElementById('endShift');
            const workDateInput = document.getElementById('workDate');

            function setDateTime(dateString, hours, minutes) {
                const [year, month, day] = dateString.split('-');
                const date = new Date(year, month - 1, day, hours, minutes);

                // Formatiranje u lokalno vrijeme
                const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
                const formattedTime = `${String(date.getHours()).padStart(2, '0')}:00`; // Uvijek 00 minute

                return `${formattedDate}T${formattedTime}`;
            }

             function adjustMinutesToZero(input) {
                if(!input.value) return;

                const [datePart, timePart] = input.value.split('T');
                let [hours, minutes] = timePart.split(':');

                if(minutes !== '00') {
                    input.value = `${datePart}T${hours}:00`;
                }
            }

            // Dodajemo event listenere za automatsku korekciju
            startTimeInput.addEventListener('change', function() {
                adjustMinutesToZero(this);
            });

            endTimeInput.addEventListener('change', function() {
                adjustMinutesToZero(this);
            });


            btnDnevna.addEventListener('click', function() {
                const selectedDate = workDateInput.value;
                startTimeInput.value = setDateTime(selectedDate, 7, 0);
                endTimeInput.value = setDateTime(selectedDate, 19, 0);

            });

            btnNocna.addEventListener('click', function() {
                const selectedDate = workDateInput.value;
                const nextDay = new Date(selectedDate);
                nextDay.setDate(nextDay.getDate() + 1);

                startTimeInput.value = setDateTime(selectedDate, 19, 0);
                endTimeInput.value = setDateTime(nextDay.toISOString().split("T")[0], 7, 0);

            });

            const workTimeForm = document.getElementById('workTimeForm');

        workTimeForm.addEventListener('submit', function(event) {
            const start = startTimeInput.value;
            const end = endTimeInput.value;

            const startDate = new Date(start);
            const endDate = new Date(end);

            // Provjera da kraj nije prije početka
            if(endDate <= startDate) {
                document.getElementById("errorMessage").innerHTML = "Početak rada mora biti prije završetka!";
                event.preventDefault();
                return;
            }

            // Provjera trajanja smjene
            const diffMs = endDate - startDate;
            const diffHours = diffMs / (1000 * 60 * 60);

            if(diffHours < 1) {
                document.getElementById("errorMessage").innerHTML = "Minimalno trajanje radnog vremena je 1 sat!";
                event.preventDefault();
                return;
            }

            if(diffHours > 24) {
                document.getElementById("errorMessage").innerHTML = "Najduže trajanje smjene je 24 sata!";
                event.preventDefault();
                return;
            }
        });
    });