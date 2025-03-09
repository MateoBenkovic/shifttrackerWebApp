package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkTypesOther;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkTypesOtherRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkTypesOtherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // select all work types other
    public List<WorkTypesOther> findAll() {
        String sql = "SELECT id, work_type_num, work_type_name, account_num, coefficient " +
                     "FROM work_types_other";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkTypesOther workTypesOther = new WorkTypesOther();

            workTypesOther.setId(rs.getInt("id"));
            workTypesOther.setWorkTypeNum(rs.getString("work_type_num"));
            workTypesOther.setWorkTypeName(rs.getString("work_type_name"));
            workTypesOther.setAccountNum(rs.getString("account_num"));
            workTypesOther.setCoefficient(rs.getBigDecimal("coefficient"));

            return workTypesOther;
        });
    }
}
