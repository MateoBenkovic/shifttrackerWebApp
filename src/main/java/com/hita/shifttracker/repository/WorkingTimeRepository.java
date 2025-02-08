package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Integer> {
}
