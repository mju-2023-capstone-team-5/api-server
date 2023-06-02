package com.team5.capstone.mju.apiserver.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Home Controller")
public class HomeController {
    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("capstone project");
    }
}
