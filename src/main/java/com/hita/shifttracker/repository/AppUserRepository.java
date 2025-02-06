package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
}
