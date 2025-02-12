package com.hita.shifttracker.repository;

import com.hita.shifttracker.model.WorkingTimeUserWtCalView;
import com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO;
import com.hita.shifttracker.model.WorkingTimeUserWtCalViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingTimeUserWtCalViewRepository extends JpaRepository<WorkingTimeUserWtCalView, WorkingTimeUserWtCalViewId> {
//
//    @Query("SELECT new com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO(wt.workTypeName, wt.day01, wt.day02, wt.day03, " +
//            "wt.day04, wt.day05, wt.day06, wt.day07, wt.day08, wt.day09, wt.day10, wt.day11, wt.day12, wt.day13, wt.day14, " +
//            "wt.day15, wt.day16, wt.day17, wt.day18, wt.day19, wt.day20, wt.day21, wt.day22, wt.day23, wt.day24, wt.day25, " +
//            "wt.day26, wt.day27, wt.day28, wt.day29, wt.day30, wt.day31, wt.total) FROM WorkingTimeUserWtCalView wt WHERE wt.appUserCode = :appUserCode")
//    List<WorkingTimeUserWtCalViewDTO> findAllRecords(@Param("appUserCode") String appUserCode);

//
//    @Query("SELECT new com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO(wt.workTypeName) FROM WorkingTimeUserWtCalView wt WHERE wt.appUserCode = :appUserCode")
//    List<WorkingTimeUserWtCalViewDTO> findAllRecords(@Param("appUserCode") String appUserCode);

//
//    ----------------------------------------------

    // ovo ovdje ispod odkomentiraj

    @Query("SELECT new com.hita.shifttracker.model.WorkingTimeUserWtCalViewDTO(wt.workTypeName, wt.day01, wt.day02, wt.day03, wt.day04, wt.day05, wt.day06, wt.day07, wt.day08, wt.day09, wt.day10, wt.day11, wt.day12, wt.day13, wt.day14, wt.day15, wt.day16, wt.day17, wt.day18, wt.day19, wt.day20, wt.day21, wt.day22, wt.day23, wt.day24, wt.day25, wt.day26, wt.day27, wt.day28, wt.day29, wt.day30, wt.day31) FROM WorkingTimeUserWtCalView wt WHERE wt.appUserCode = :appUserCode AND wt.month = :month AND wt.year = :year")
    List<WorkingTimeUserWtCalViewDTO> findAllRecords(@Param("appUserCode") String appUserCode, @Param("month") Integer month, @Param("year") Integer year);


    @Query(value = "SELECT naziv_vr_rada, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 FROM vw_working_time_user_wt_cal WHERE us_code = :appUserCode AND mjesec = :month AND godina = :year", nativeQuery = true)
    List<WorkingTimeUserWtCalView> findRecords(@Param("appUserCode") String appUserCode, @Param("month") Integer month, @Param("year") Integer year);
}
