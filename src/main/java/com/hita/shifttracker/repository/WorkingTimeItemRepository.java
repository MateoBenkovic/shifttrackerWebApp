package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTimeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingTimeItemRepository extends JpaRepository<WorkingTimeItem, Integer> {
}
