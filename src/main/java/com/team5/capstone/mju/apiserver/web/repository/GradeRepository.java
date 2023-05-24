package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByParkingLotId(Integer parkingLotId);
    List<Grade> findByUserId(Integer userId);

    List<Grade> findTop5ByParkingLotIdOrderByTimestampDesc(Integer parkingLotId);
}