package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    List<AppUser> findAllByAppRoleId(int id);

    // find app user code by user id
    @Query(value = "SELECT user_code FROM app_user WHERE id = :appUserId", nativeQuery = true)
    String findAppUserCodeById(@Param("appUserId") int appUserId);
}
