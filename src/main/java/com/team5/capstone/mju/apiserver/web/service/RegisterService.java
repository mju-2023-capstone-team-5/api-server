package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.RegisterRequestDto;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerNewUser(RegisterRequestDto registerRequestDto) {
        return;
    }
}
