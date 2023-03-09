package com.team5.capstone.mju.apiserver.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("capstone project");
    }
}
