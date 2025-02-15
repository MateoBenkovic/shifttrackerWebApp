package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // find all app users
    public List<AppUserDTO> findAllAppUsers() {
        String sql = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, u.user_code, " +
                     "r.name AS role_name, r.user_type_code AS role_user_type_code, " +
                     "t.name AS team_name, " +
                     "o.name AS org_unit_name " +
                     "FROM app_user u " +
                     "LEFT JOIN app_role r ON u.app_role_id = r.id " +
                     "LEFT JOIN team t ON u.team_id = t.id " +
                     "LEFT JOIN org_unit o ON u.org_unit_id = o.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AppUserDTO appUserDTO = new AppUserDTO();

            appUserDTO.setId(rs.getInt("id"));
            appUserDTO.setFirstName(rs.getString("first_name"));
            appUserDTO.setLastName(rs.getString("last_name"));
            appUserDTO.setEmail(rs.getString("email"));
            appUserDTO.setPassword(rs.getString("password"));
            appUserDTO.setUserCode(rs.getString("user_code"));
            appUserDTO.setRoleName(rs.getString("role_name"));
            appUserDTO.setRoleUserTypeCode(rs.getString("role_user_type_code"));
            appUserDTO.setTeamName(rs.getString("team_name"));
            appUserDTO.setOrgUnitName(rs.getString("org_unit_name"));

            return appUserDTO;
        });
    }

    // find all employees
    public List<AppUserDTO> findAllEmployees() {
        String sql = "SELECT u.id, u.first_name, u.last_name, u.user_code, r.name AS role_name, " +
                     "t.name AS team_name, o.name AS org_unit_name, tr.name AS team_role_name " +
                     "FROM app_user u " +
                     "LEFT JOIN app_role r ON u.app_role_id = r.id " +
                     "LEFT JOIN team t ON u.team_id = t.id " +
                     "LEFT JOIN org_unit o ON u.org_unit_id = o.id " +
                     "LEFT JOIN team_role tr ON u.team_role_id = tr.id " +
                     "WHERE r.user_type_code = 'R'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AppUserDTO employee = new AppUserDTO();

            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setUserCode(rs.getString("user_code"));
            employee.setRoleName(rs.getString("role_name"));
            employee.setTeamName(rs.getString("team_name"));
            employee.setOrgUnitName(rs.getString("org_unit_name"));
            employee.setTeamRoleName(rs.getString("team_role_name"));

            return employee;
        });
    }

    // find employee by id
    public AppUserDTO findEmployeeById(int appUserId) {
        String sql = "SELECT u.id, u.first_name, u.last_name, u.user_code, r.name AS role_name, " +
                "t.name AS team_name, o.name AS org_unit_name, tr.name AS team_role_name " +
                "FROM app_user u " +
                "LEFT JOIN app_role r ON u.app_role_id = r.id " +
                "LEFT JOIN team t ON u.team_id = t.id " +
                "LEFT JOIN org_unit o ON u.org_unit_id = o.id " +
                "LEFT JOIN team_role tr ON u.team_role_id = tr.id " +
                "WHERE u.id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{appUserId}, (rs, rowNum) -> {
            AppUserDTO employee = new AppUserDTO();

            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setUserCode(rs.getString("user_code"));
            employee.setRoleName(rs.getString("role_name"));
            employee.setTeamName(rs.getString("team_name"));
            employee.setOrgUnitName(rs.getString("org_unit_name"));
            employee.setTeamRoleName(rs.getString("team_role_name"));

            return employee;
        });
    }

    // add employee
    public void insertEmployee(AppUser employee) {
        String sql = "INSERT INTO app_user (first_name, last_name, email, password, telephone, oib, " +
                     "org_unit_id, work_role_id, team_id, team_role_id, coefficient, app_role_id, user_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, employee.getFirstName(), employee.getFirstName(), employee.getEmail(),
                employee.getPassword(), employee.getTelephone(), employee.getOib(), employee.getOrganizationUnitId(),
                employee.getWorkRoleId(), employee.getTeamId(), employee.getTeamRoleId(), employee.getCoefficient(),
                employee.getAppRole(), employee.getAppUserCode());
    }
}

