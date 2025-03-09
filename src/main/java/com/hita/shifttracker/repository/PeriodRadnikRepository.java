package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.PeriodRadnik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeriodRadnikRepository {

    private final JdbcTemplate jdbcTemplate;

    public PeriodRadnikRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // find all
    public List<PeriodRadnik> findAll() {
        String sql = "SELECT * FROM period_radnik";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PeriodRadnik periodRadnik = new PeriodRadnik();

            periodRadnik.setId(rs.getInt("id"));
            periodRadnik.setAppUserId(rs.getInt("app_user_id"));
            periodRadnik.setPeriodId(rs.getInt("period_id"));
            periodRadnik.setStatus(rs.getString("status"));
            periodRadnik.setInsertedAt(rs.getTimestamp("inserted_at"));
            periodRadnik.setUpdatedAt(rs.getTimestamp("updated_at"));
            periodRadnik.setUidInsUpd(rs.getInt("uid_ins_upd"));
            periodRadnik.setYear(rs.getInt("year"));
            periodRadnik.setMonth(rs.getInt("month"));

            return periodRadnik;
        });
    }


}
