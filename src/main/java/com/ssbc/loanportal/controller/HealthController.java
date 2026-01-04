package com.ssbc.loanportal.controller;

import com.ssbc.loanportal.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("Service is running", "UP");
    }
}
