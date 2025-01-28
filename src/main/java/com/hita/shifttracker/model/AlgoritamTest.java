package com.hita.shifttracker.model;

import java.time.LocalDateTime;

public class AlgoritamTest {

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    //split pattern, kako ga dijelimo
    private LocalDateTime jutarnja;
    private LocalDateTime popodnevna;
    private LocalDateTime nocna;

    /*
    private WorkHours calculateWorkHours(EmployeeShift shift) {
        LocalDateTime start = shift.getStartShift();
        LocalDateTime end = shift.getEndShift();

        int firstShiftHours = 0;
        int secondShiftHours = 0;
        int nightShiftHours = 0;

        LocalDateTime current = start;

        while (current.isBefore(end)) {
            int hour = current.getHour();
            if (hour >= 6 && hour < 14) {
                firstShiftHours++;
            } else if (hour >= 14 && hour < 22) {
                secondShiftHours++;
            } else {
                nightShiftHours++;
            }
            current = current.plusHours(1);
        }

        // Kreiraj i vrati WorkHours objekt
        WorkHours workHours = new WorkHours();
        workHours.setEmployeeShift(shift);
        workHours.setFirstShiftHours(firstShiftHours);
        workHours.setSecondShiftHours(secondShiftHours);
        workHours.setNightShiftHours(nightShiftHours);

        return workHours;
    }

    metoda prima EmployeeWorkHour i raščlanjuje sate na pojedine vrste rada i sve se sprema u tablice prema modelu
     */
}
