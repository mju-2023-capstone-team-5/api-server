package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.GradeRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.GradeResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.Grade;
import com.team5.capstone.mju.apiserver.web.exceptions.GradeNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.ParkingLotNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.UserNotFoundException;
import com.team5.capstone.mju.apiserver.web.repository.GradeRepository;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class GradeService {

    // Repository 객체
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;


    @Autowired // 생성자를 통한 의존성 주입
    public GradeService(GradeRepository gradeRepository,
                        UserRepository userRepository,
                        ParkingLotRepository parkingLotRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    // 후기 요청(주차장 아이디)
    @Transactional(readOnly = true)
    public List<GradeResponseDto> getGradeByParkingLotId(Long parkingLotId) {
        List<Grade> grades = gradeRepository.findByParkingLotId(Math.toIntExact(parkingLotId));
        return grades.stream().map(GradeResponseDto::of).collect(Collectors.toList());
    }

    // 후기 요청(유저 아이디)
    @Transactional(readOnly = true)
    public List<GradeResponseDto> getGradeByUserId(Long userId) {
        List<Grade> grades = gradeRepository.findByUserId(Math.toIntExact(userId));
        return grades.stream().map(GradeResponseDto::of).collect(Collectors.toList());
    }

    // 후기 생성
    @Transactional
    public GradeResponseDto createGrade(GradeRequestDto requestDto) {
        userRepository.findById(Long.valueOf(requestDto.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));
        parkingLotRepository.findById(Long.valueOf(requestDto.getParkingLotId()))
                .orElseThrow(() -> new ParkingLotNotFoundException(requestDto.getParkingLotId()));

        // Grade 엔티티를 데이터베이스에 저장
        Grade savedGrade = gradeRepository.save(requestDto.ToEntity());
        return GradeResponseDto.of(savedGrade);
    }

    // 후기 업데이트
    @Transactional
    public GradeResponseDto updateGrade(Long id, GradeRequestDto requestDto) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));

        // 후기 상세 정보를 업데이트합니다.
        grade.updateAllInfoSelf(requestDto);
        return GradeResponseDto.of(grade);
    }

    // 후기 삭제
    @Transactional
    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));

        gradeRepository.deleteById(id);
    }
}
