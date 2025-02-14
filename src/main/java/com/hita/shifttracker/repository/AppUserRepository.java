package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    @Query("SELECT u.id, u.first_name, u.last_name, u.email, u.password, u.user_code, " +
            "r.name AS role_name, r.user_type_code AS role_user_type_code, " +
            "t.name AS team_name, " +
            "o.name AS org_unit_name " +
            "FROM app_user u " +
            "LEFT JOIN app_role r ON u.app_role_id = r.id " +
            "LEFT JOIN team t ON u.team_id = t.id " +
            "LEFT JOIN org_unit o ON u.org_unit_id = o.id")
    List<AppUserDTO> findAllWithAppRoleAndTeamAndOrganizationUnit();

    // find all employees

    @Query("SELECT u.id, u.first_name, u.last_name, u.user_code, " +
            "r.name AS role_name, " +
            "t.name AS team_name, " +
            "o.name AS org_unit_name, " +
            "tr.name AS team_role_name " +
            "FROM app_user u " +
            "LEFT JOIN app_role r ON u.app_role_id = r.id " +
            "LEFT JOIN team t ON u.team_id = t.id " +
            "LEFT JOIN org_unit o ON u.org_unit_id = o.id " +
            "LEFT JOIN team_role tr ON u.team_role_id = tr.id " +
            "WHERE r.user_type_code = 'R'")
    List<AppUserDTO> findAllEmployees();

    @Query("SELECT u.id, u.first_name, u.last_name, u.user_code, " +
            "r.name AS role_name, " +
            "t.name AS team_name, " +
            "o.name AS org_unit_name " +
            "FROM app_user u " +
            "LEFT JOIN app_role r ON u.app_role_id = r.id " +
            "LEFT JOIN team t ON u.team_id = t.id " +
            "LEFT JOIN org_unit o ON u.org_unit_id = o.id " +
            "WHERE u.id = :id")
    AppUserDTO findEmployeeById(int id);

    @Modifying
    @Transactional
    @Query("INSERT INTO app_user(email, first_name, last_name, oib, password, telephone, " +
            "app_role_id, org_unit_id, team_id, team_role_id, user_code, work_role_id, coefficient)" +
            "VALUES (:email, :firstName, :lastName, :oib, :password, :telephone, " +
            ":appRoleId, :orgUnitId, :teamId, :teamRoleId, :userCode, :workRoleId, :coefficient)")
    void addNewEmployee(
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("oib") String oib,
            @Param("password") String password,
            @Param("telephone") String telephone,
            @Param("appRoleId") int appRoleId,
            @Param("orgUnitId") Integer orgUnitId,
            @Param("teamId") Integer teamId,
            @Param("teamRoleId") Integer teamRoleId,
            @Param("userCode") String userCode,
            @Param("workRoleId") Integer workRoleId,
            @Param("coefficient") BigDecimal coefficient
    );

}
