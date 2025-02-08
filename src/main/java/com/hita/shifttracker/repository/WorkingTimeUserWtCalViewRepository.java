package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingTimeUserWtCalViewRepository extends JpaRepository<WorkingTimeUserWtCalView, Integer> {


}
