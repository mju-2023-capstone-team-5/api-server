package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
