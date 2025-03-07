package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.WorkingTimeReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WorkHourReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkHourReportRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WorkingTimeReportDTO> getEmployeeReport(List<Integer> appUserIds, int month, int year) {
        if (appUserIds == null || appUserIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Dinamički kreiramo "?, ?, ?" za IN klauzulu
        String inSql = appUserIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String sql = "SELECT \"ime\", \"prezime\", \"org_unit_id\", \"org_unit_name\", \"mjesec\", \"godina\", \"Redovni\", \"Prekovremeni\", \"Odsustva\", \"Ukupno\" " +
                "FROM \"test_view\" " +
                "WHERE \"id_kor\" IN (" + inSql + ") AND \"mjesec\" = ? AND \"godina\" = ?";

        // Kreiramo listu parametara
        List<Object> params = new ArrayList<>(appUserIds);
        params.add(month);
        params.add(year);

        System.out.println("SQL UPIT: " + sql);
        System.out.println("PARAMETRI: " + params);


        return jdbcTemplate.query(sql, params.toArray(),
                (rs, rowNum) -> {
                    System.out.println("DOHVATIO IZ BAZE: " +
                            rs.getString("ime") + " " +
                            rs.getString("prezime") + " " +
                            rs.getBigDecimal("org_unit_id") + " " +
                            rs.getBigDecimal("mjesec") + " " +
                            rs.getBigDecimal("godina") + " " +
                            (rs.getBigDecimal("Redovni") != null ? rs.getBigDecimal("Redovni").doubleValue() : 0.0) + " " +
                            (rs.getBigDecimal("Prekovremeni") != null ? rs.getBigDecimal("Prekovremeni").doubleValue() : 0.0) + " " +
                            (rs.getBigDecimal("Odsustva") != null ? rs.getBigDecimal("Odsustva").doubleValue() : 0.0) + " " +
                            (rs.getBigDecimal("Ukupno") != null ? rs.getBigDecimal("Ukupno").doubleValue() : 0.0)
                    );

                    return new WorkingTimeReportDTO(
                            rs.getString("ime"),
                            rs.getString("prezime"),
                            rs.getBigDecimal("org_unit_id"),
                            rs.getString("org_unit_name"),
                            rs.getInt("mjesec"),
                            rs.getInt("godina"),
                            rs.getBigDecimal("Redovni"),
                            rs.getBigDecimal("Prekovremeni"),
                            rs.getBigDecimal("Odsustva"),
                            rs.getBigDecimal("Ukupno")
                    );
                }
        );

    }

}
