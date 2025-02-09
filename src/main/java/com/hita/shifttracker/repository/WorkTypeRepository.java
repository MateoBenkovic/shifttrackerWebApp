package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
}
