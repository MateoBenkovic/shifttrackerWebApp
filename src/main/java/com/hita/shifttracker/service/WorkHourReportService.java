package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.WorkingTimeReportDTO;
import com.hita.shifttracker.repository.WorkHourReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkHourReportService {

    private final WorkHourReportRepository workHourReportRepository;

    public WorkHourReportService(WorkHourReportRepository workHourReportRepository) {
        this.workHourReportRepository = workHourReportRepository;
    }

    public List<WorkingTimeReportDTO> getEmployeeReport(List<Integer> appUserIds, int month, int year) {
        return workHourReportRepository.getEmployeeReport(appUserIds, month, year);
    }

    public List<WorkingTimeReportDTO> getOrgUnitEmployeeReport(int orgUnitId, int month, int year) {
        return workHourReportRepository.getOrgUnitEmployeeReport(orgUnitId, month, year);
    }

    public WorkingTimeReportDTO getOrgUnitReport(int orgUnitId, int month, int year) {
        return workHourReportRepository.getOrgUnitWorkSummary(orgUnitId, month, year);
    }

}
