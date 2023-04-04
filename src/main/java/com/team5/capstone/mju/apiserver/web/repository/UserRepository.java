package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}