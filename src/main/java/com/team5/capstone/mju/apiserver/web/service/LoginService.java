package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.LoginRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.LoginResponseDto;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidateToken() {
        return false;
    }

    public boolean createOrFound() {
        return true;
    }

    public LoginResponseDto tryLogin(LoginRequestDto loginRequestDto) {
        return LoginResponseDto.builder()
                .jwt(null)
                .userId(null)
                .isNewUser(true)
                .build();
    }
}
