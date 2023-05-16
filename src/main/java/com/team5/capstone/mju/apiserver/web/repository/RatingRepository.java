package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating findByParkingLotId(Integer parkingLotId);
}