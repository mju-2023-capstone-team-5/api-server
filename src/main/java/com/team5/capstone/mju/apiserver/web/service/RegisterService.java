package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerNewUser(RegisterRequestDto registerRequestDto) {
        Optional<User> found = userRepository.findById(registerRequestDto.getUserId());

        if (found.isEmpty()) throw new RuntimeException();

        User user = found.get();
        user.addExtraInfoSelf(registerRequestDto); // User 객체 스스로 추가 정보 삽입
        return;
    }
}
