package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.service.CustomerSupportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "주차장 Controller", description = "주차장 관련 API 요청 Controller")
public class CustomerSupportController {
    private final CustomerSupportService customerSupportService;

    @Autowired
    public CustomerSupportController(CustomerSupportService customerSupportService) {
        this.customerSupportService = customerSupportService;
    }


}
