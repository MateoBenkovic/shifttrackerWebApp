package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeItemDTO;
import com.hita.shifttracker.repository.WorkingTimeItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkingTimeItemService {

    private final WorkingTimeItemRepository workingTimeItemRepository;

    public WorkingTimeItemService (WorkingTimeItemRepository workingTimeItemRepository) {
        this.workingTimeItemRepository = workingTimeItemRepository;
    }

    public List<Map<String, Object>> getWorkingTimeItemByDays(int appUserId, int month, int year) {

        List<WorkingTimeItemDTO> items = workingTimeItemRepository.findByEmployeeIdAndMonthAndYear(appUserId, month, year);
        Map<String, Map<String, Object>> transposedData = new LinkedHashMap<>();

        for (WorkingTimeItemDTO item : items) {
            String key = item.getWorkTypeNum() + "_" + item.getWorkTypeName() + "_" + item.getAccountNum();
            Map<String, Object> dayData = transposedData.getOrDefault(key, new LinkedHashMap<>());
            dayData.put("work_type_name", item.getWorkTypeName());
            dayData.put("work_type_num", item.getWorkTypeNum());
            dayData.put("account_num", item.getAccountNum());

            // Convert the date into day (1-31)
            int day = item.getDate().getDayOfMonth();
            dayData.put("day" + day, item.getWorkTypeCode());

            // Store the data back in the map
            transposedData.put(key, dayData);
        }

        // Convert the map into a list to send back as response
        return new ArrayList<>(transposedData.values());
    }

}
