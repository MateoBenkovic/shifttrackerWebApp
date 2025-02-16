package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeItemDTO;
import com.hita.shifttracker.dto.WorkingTimeItemTotalHourDTO;
import com.hita.shifttracker.repository.WorkingTimeItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class WorkingTimeItemService {

    private final WorkingTimeItemRepository workingTimeItemRepository;

    public WorkingTimeItemService (WorkingTimeItemRepository workingTimeItemRepository) {
        this.workingTimeItemRepository = workingTimeItemRepository;
    }

    public List<Map<String, Object>> getWorkingTimeItemByDays(int appUserId, int month, int year) {

        List<WorkingTimeItemDTO> items = workingTimeItemRepository.findItemByEmployeeIdAndMonthAndYear(appUserId, month, year);
        Map<String, Map<String, Object>> transposedData = new LinkedHashMap<>();

        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();

        for (WorkingTimeItemDTO item : items) {
            String key = item.getWorkTypeNum() + "_" + item.getWorkTypeName() + "_" + item.getAccountNum();
            Map<String, Object> dayData = transposedData.getOrDefault(key, new LinkedHashMap<>());
            dayData.put("work_type_name", item.getWorkTypeName());
            dayData.put("work_type_num", item.getWorkTypeNum());
            dayData.put("account_num", item.getAccountNum());

            int day = item.getDate().getDayOfMonth();
            dayData.put("day" + day, item.getTotalHours());

            transposedData.put(key, dayData);
        }

        for (Map<String, Object> dayData : transposedData.values()) {
            for (int i = 1; i <= daysInMonth; i++) {
                // If the day is not already present, add a null value (or 0, based on your preference)
                dayData.putIfAbsent("day" + i, null);
            }
        }

        // Convert the map into a list to send back as response
        return new ArrayList<>(transposedData.values());
    }

//    public Map<String, List<Integer>> getFormattedWorkingTimeData(int appUserId, int month, int year) {
//        List<WorkingTimeItemTotalHourDTO> items = workingTimeItemRepository.findTotalHoursByEmployeeIdAndMonthAndYear(appUserId, month, year);
//
//        // Prepare a map for the result
//        Map<String, List<Integer>> formattedData = new LinkedHashMap<>();
//        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
//
//        // Initialize empty lists for the days in the month (for display)
//        List<Integer> hoursFrom = new ArrayList<>(Collections.nCopies(daysInMonth, null));
//        List<Integer> hoursTo = new ArrayList<>(Collections.nCopies(daysInMonth, null));
//        List<Integer> totalHours = new ArrayList<>(Collections.nCopies(daysInMonth, null));
//
//        // Populate the lists with the data
//        for (WorkingTimeItemTotalHourDTO item : items) {
//            int day = item.getDate().getDayOfMonth() - 1; // 0-based index for days
//            hoursFrom.set(day, item.getHoursFrom());
//            hoursTo.set(day, item.getHoursTo());
//            totalHours.set(day, item.getTotalHours());
//        }
//
//        // Add to the map in the desired format
//        formattedData.put("hoursFrom", hoursFrom);
//        formattedData.put("hoursTo", hoursTo);
//        formattedData.put("totalHours", totalHours);
//
//
//        return formattedData;
//    }

    public Map<String, Object> getFormattedWorkingTimeData(int appUserId, int month, int year) {
        List<WorkingTimeItemTotalHourDTO> items = workingTimeItemRepository.findTotalHoursByEmployeeIdAndMonthAndYear(appUserId, month, year);

        // Calculate the number of days in the given month
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth(); // Will be 28, 29, 30, or 31 depending on the month

        // Initialize empty lists for the days in the month (for display)
        List<Integer> hoursFrom = new ArrayList<>(Collections.nCopies(daysInMonth, null));
        List<Integer> hoursTo = new ArrayList<>(Collections.nCopies(daysInMonth, null));
        List<Integer> totalHours = new ArrayList<>(Collections.nCopies(daysInMonth, null));

        // Populate the lists with the data
        for (WorkingTimeItemTotalHourDTO item : items) {
            int day = item.getDate().getDayOfMonth() - 1; // 0-based index for days
            hoursFrom.set(day, item.getHoursFrom());
            hoursTo.set(day, item.getHoursTo());
            totalHours.set(day, item.getTotalHours());
        }

        // Add the lists to the formatted data map
        Map<String, Object> formattedData = new LinkedHashMap<>();
        formattedData.put("hoursFrom", hoursFrom);
        formattedData.put("hoursTo", hoursTo);
        formattedData.put("totalHours", totalHours);
        formattedData.put("daysInMonth", daysInMonth); // Add days in month to handle display

        return formattedData;
    }


}
