package com.hita.shifttracker.service;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import com.hita.shifttracker.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

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
        employee.setAppRoleId(1);
        employee.setCoefficient(new BigDecimal(1.0));
        // generate user code based on first name last name and 6 characters and unique
        String appUserCode = generateAppUserCode(employee);
        while (appUserRepository.existsByUserCode(appUserCode)) {
            appUserCode = generateAppUserCode(employee);
        }

        employee.setAppUserCode(appUserCode);

        appUserRepository.insertEmployee(employee);
    }

    private String generateAppUserCode(AppUser employee) {
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        String randomCode = generateRandomCode(6);
        String userCode = firstName.substring(0, 2) + lastName.substring(0, 2) + randomCode;
        return userCode.substring(0, Math.min(5, userCode.length())).toUpperCase();
    }

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomCode.append(characters.charAt(index));
        }
        return randomCode.toString();
    }
}
