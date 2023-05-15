package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.UserPayReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPayReceiptRepository extends JpaRepository<UserPayReceipt, Long> {
}