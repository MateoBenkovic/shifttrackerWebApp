package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.WorkingTimeReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

        return jdbcTemplate.query(sql, params.toArray(),
                (rs, rowNum) -> new WorkingTimeReportDTO(
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getInt("org_unit_id"),
                        rs.getString("org_unit_name"),
                        rs.getInt("mjesec"),
                        rs.getInt("godina"),
                        rs.getBigDecimal("Redovni"),
                        rs.getBigDecimal("Prekovremeni"),
                        rs.getBigDecimal("Odsustva"),
                        rs.getBigDecimal("Ukupno")
                )
        );
    }

    public List<WorkingTimeReportDTO> getOrgUnitEmployeeReport(int orgUnitId, int month, int year) {
        String sql = "SELECT \"ime\", \"prezime\", \"org_unit_id\", \"org_unit_name\", \"mjesec\", \"godina\", \"Redovni\", \"Prekovremeni\", \"Odsustva\", \"Ukupno\" " +
                "FROM \"test_view\" " +
                "WHERE \"org_unit_id\" = ? AND \"mjesec\" = ? AND \"godina\" = ?";

        return jdbcTemplate.query(sql, new Object[]{orgUnitId, month, year},
                (rs, rowNum) -> new WorkingTimeReportDTO(
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getInt("org_unit_id"),
                        rs.getString("org_unit_name"),
                        rs.getInt("mjesec"),
                        rs.getInt("godina"),
                        rs.getBigDecimal("Redovni") != null ? rs.getBigDecimal("Redovni") : BigDecimal.ZERO,
                        rs.getBigDecimal("Prekovremeni") != null ? rs.getBigDecimal("Prekovremeni") : BigDecimal.ZERO,
                        rs.getBigDecimal("Odsustva") != null ? rs.getBigDecimal("Odsustva") : BigDecimal.ZERO,
                        rs.getBigDecimal("Ukupno") != null ? rs.getBigDecimal("Ukupno") : BigDecimal.ZERO
                )
        );
    }

    public WorkingTimeReportDTO getOrgUnitWorkSummary(int orgUnitId, int month, int year) {
        String sql = "SELECT \"org_unit_id\", \"org_unit_name\", \"mjesec\", \"godina\", " +
                "SUM(\"Redovni\") AS total_redovan, " +
                "SUM(\"Prekovremeni\") AS total_prekovremeni, " +
                "SUM(\"Odsustva\") AS total_odsustva, " +
                "SUM(\"Ukupno\") AS total_ukupno " +
                "FROM \"test_view\" " +
                "WHERE \"org_unit_id\" = ? AND \"mjesec\" = ? AND \"godina\" = ? " +
                "GROUP BY \"org_unit_id\", \"org_unit_name\", \"mjesec\", \"godina\"";

        List<WorkingTimeReportDTO> results = jdbcTemplate.query(sql, new Object[]{orgUnitId, month, year},
                (rs, rowNum) -> new WorkingTimeReportDTO(
                        rs.getInt("org_unit_id"),
                        rs.getString("org_unit_name"),
                        rs.getInt("mjesec"),
                        rs.getInt("godina"),
                        rs.getBigDecimal("total_redovan"),
                        rs.getBigDecimal("total_prekovremeni"),
                        rs.getBigDecimal("total_odsustva"),
                        rs.getBigDecimal("total_ukupno")
                )
        );

        if (results.isEmpty()) {
            String orgUnitNameQuery = "SELECT \"name\" FROM \"org_unit\" WHERE \"id\" = ?";
            String orgUnitName = jdbcTemplate.queryForObject(orgUnitNameQuery, new Object[]{orgUnitId}, String.class);

            return new WorkingTimeReportDTO(
                    orgUnitId, orgUnitName, month, year,
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
            );
        }

        return results.get(0);
    }
}
