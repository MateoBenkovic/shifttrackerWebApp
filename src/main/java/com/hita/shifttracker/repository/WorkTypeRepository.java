package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkTypeRepository extends CrudRepository<WorkType, Integer> {

    // select redovan rad
    // work_type_num work_type_name account_num where owertime_indicator = 'N'
    @Query("SELECT wt.work_type_num, wt.work_type_name, wt.account_num " +
            "FROM work_types wt" +
            "WHERE wt.overtime_indicator = 'N'")
    List<WorkType> findAllRegularWorkTypes ();
}
