package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.OrgUnitWorkingTimeReportDTO;
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

    public WorkHourReportRepository(JdbcTemplate jdbcTemplate) {
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

    public List<OrgUnitWorkingTimeReportDTO> getOrgUnitWorkSummary(int orgUnitId, int month, int year) {
        String sql = "SELECT w.godina, w.mjesec, o.name AS org_unit_name, w.naziv_vr_rada AS vrsta_rada, " +
                "COALESCE(SUM(w.\"Ukupno\"), 0) AS ukupni_sati, " +
                "(SELECT COALESCE(SUM(w2.\"Ukupno\"), 0) " +
                " FROM vw_working_time_user_all_wt_cal w2 " +
                " JOIN app_user u2 ON w2.us_code = u2.user_code " +
                " JOIN org_unit o2 ON u2.org_unit_id = o2.id " +
                " WHERE w2.godina = ? AND w2.mjesec = ? AND o2.id = ?) AS ukupno_sati_sve_vrste " +
                "FROM vw_working_time_user_all_wt_cal w " +
                "JOIN app_user u ON w.us_code = u.user_code " +
                "JOIN org_unit o ON u.org_unit_id = o.id " +
                "WHERE w.godina = ? AND w.mjesec = ? AND o.id = ? " +
                "GROUP BY w.godina, w.mjesec, o.name, w.naziv_vr_rada " +
                "ORDER BY w.naziv_vr_rada";

        return jdbcTemplate.query(sql, new Object[]{year, month, orgUnitId, year, month, orgUnitId},
                (rs, rowNum) -> new OrgUnitWorkingTimeReportDTO(
                        orgUnitId,
                        rs.getString("org_unit_name"),  // Ispravan naziv stupca
                        rs.getInt("mjesec"),
                        rs.getInt("godina"),
                        rs.getString("vrsta_rada"),
                        rs.getBigDecimal("ukupni_sati"),
                        rs.getBigDecimal("ukupno_sati_sve_vrste")
                )
        );

    }

    public List<OrgUnitWorkingTimeReportDTO> getAllOrgUnitWorkSummary(int month, int year) {
        String sql = "SELECT w.mjesec, w.godina, w.naziv_vr_rada AS vrsta_rada, " +
                "COALESCE(SUM(w.\"Ukupno\"), 0) AS ukupni_sati, " +
                "(SELECT COALESCE(SUM(w2.\"Ukupno\"), 0) " +
                " FROM vw_working_time_user_all_wt_cal w2 " +
                " WHERE w2.godina = ? AND w2.mjesec = ?) AS ukupno_sati_sve_vrste " +
                "FROM vw_working_time_user_all_wt_cal w " +
                "WHERE w.godina = ? AND w.mjesec = ? " +
                "GROUP BY w.mjesec, w.godina, w.naziv_vr_rada " +
                "ORDER BY w.naziv_vr_rada";

        return jdbcTemplate.query(sql, new Object[]{year, month, year, month},
                (rs, rowNum) -> new OrgUnitWorkingTimeReportDTO(
                        month,
                        year,
                        rs.getString("vrsta_rada"),
                        rs.getBigDecimal("ukupni_sati"),
                        rs.getBigDecimal("ukupno_sati_sve_vrste")
                )
        );
    }
}


