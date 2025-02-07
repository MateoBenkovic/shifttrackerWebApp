package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    List<AppUser> findAllByAppRoleId(int id);
}
