package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
import com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO;
import com.hita.shifttracker.model.WorkingTimeUserWtCalViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingTimeUserWtCalViewRepository extends JpaRepository<WorkingTimeUserWtCalView, WorkingTimeUserWtCalViewId> {

    @Query("SELECT new com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO(wt.workTypeName) FROM WorkingTimeUserWtCalView wt WHERE wt.appUserCode = :appUserCode")
    List<WorkingTimeUserWtCalViewDTO> findAllRecords(@Param("appUserCode") String appUserCode);
}
