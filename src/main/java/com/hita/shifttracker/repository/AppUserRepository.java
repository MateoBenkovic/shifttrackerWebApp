package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    List<AppUser> findAll();

    @Query("SELECT * FROM app_user u " +
            "LEFT JOIN app_role r ON u.app_role_id = r.id " +
            "LEFT JOIN team t ON u.team_id = t.id " +
            "LEFT JOIN org_unit o ON u.org_unit_id = o.id")
    List<AppUser> findAllWithAppRoleAndTeamAndOrganizationUnit();

}
