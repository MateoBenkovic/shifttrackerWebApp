package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.DayLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayLabelRepository extends JpaRepository<DayLabel, String> {
}
