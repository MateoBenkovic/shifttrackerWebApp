package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTimeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WorkingTimeItemRepository extends JpaRepository<WorkingTimeItem, Integer> {

    // Exists by user id and shift id
   //boolean existsByAppUserIdAndDateAndWorkType(int appUserId, LocalDate date, int workTypeCode);
}
