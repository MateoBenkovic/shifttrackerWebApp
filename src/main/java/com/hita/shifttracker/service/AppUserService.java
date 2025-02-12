package com.hita.shifttracker.service;

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

    public List<AppUser> getAllAppUsersWithTeamAndOrganizationUnit() {
        return appUserRepository.findAllWithAppRoleAndTeamAndOrganizationUnit();
    }
}
