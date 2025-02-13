package com.hita.shifttracker.repository;

import com.hita.shifttracker.dto.AppUserDTO;
import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
            "o.name AS org_unit_name " +
            "FROM app_user u " +
            "LEFT JOIN app_role r ON u.app_role_id = r.id " +
            "LEFT JOIN team t ON u.team_id = t.id " +
            "LEFT JOIN org_unit o ON u.org_unit_id = o.id " +
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

}
