package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
}
