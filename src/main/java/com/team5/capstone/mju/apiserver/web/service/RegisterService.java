package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerNewUser(RegisterRequestDto registerRequestDto) {
        User found = userRepository.findById(registerRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User를 찾을 수 없습니다"));

        found.addExtraInfoSelf(registerRequestDto); // User 객체 스스로 추가 정보 삽입
    }
}
