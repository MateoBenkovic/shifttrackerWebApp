package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // find all employees
    public List<AppUserDTO> getAllAppUsersWithTeamAndOrganizationUnit() {
        return appUserRepository.findAllAppUsers();
    }

    // get All employees
    public List<AppUserDTO> getAllEmployees() {
        return appUserRepository.findAllEmployees();
    }

    // get employee by id
    public AppUserDTO getEmployeeById(int appUserId) {
        return appUserRepository.findEmployeeById(appUserId);
    }

    // add new Employee
    public void saveEmployee(AppUser employee) {
        appUserRepository.insertEmployee(employee);
    }
}
