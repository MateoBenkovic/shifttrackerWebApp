package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.EmployeeWorkHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWorkHourRepository extends JpaRepository<EmployeeWorkHour, Integer> {
    List<EmployeeWorkHour> findByAppUserId(int id);
}