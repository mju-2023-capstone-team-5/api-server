package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.entity.Rating;
import com.team5.capstone.mju.apiserver.web.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public void updateRating(Integer parkingLotId, Float rating) {
        Rating ratingEntity = ratingRepository.findById(parkingLotId)
                .orElseGet(() -> {
                    Rating newRating = new Rating();
                    newRating.setParkingLotId(parkingLotId);
                    return newRating;
                });

        int ratingNum = ratingEntity.getRatingNum() != null ? ratingEntity.getRatingNum() + 1 : 1;
        float ratingSum = (ratingEntity.getRatingSum() != null) ? ratingEntity.getRatingSum() + rating : rating;
        float ratingAvg = ratingSum / ratingNum;

        ratingEntity.setRatingNum(ratingNum);
        ratingEntity.setRatingSum((int) ratingSum);
        ratingEntity.setRatingAvg(ratingAvg);

        ratingRepository.save(ratingEntity);
    }
}