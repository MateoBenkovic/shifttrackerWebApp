package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkTypeRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // select all regular work types
    public List<WorkType> findAllRegularWorkTypes() {
        String sql = "SELECT wt.work_type_num, wt.work_type_name, wt.account_num " +
                     "FROM work_types wt " +
                     "WHERE wt.overtime_indicator = 'N'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
           WorkType workType = new WorkType();

           workType.setWorkTypeNumber(rs.getString("work_type_num"));
           workType.setWorkTypeName(rs.getString("work_type_name"));
           workType.setAccountNumber(rs.getString("account_num"));

           return workType;
        });
    }

}
