package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.SchedulePerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulePerMonthRepository extends JpaRepository<SchedulePerMonth, Integer> {
    List<SchedulePerMonth> findByAppUserId(int id);
}
