package com.ssbc.loanportal.service;

import com.ssbc.loanportal.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void sendOtp(SendOtpRequest request);

    AuthResponse verifyOtp(VerifyOtpRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);
}
