package com.ssbc.loanportal.service;

import com.ssbc.loanportal.entity.User;
import com.ssbc.loanportal.repository.UserRepository;
import com.ssbc.loanportal.dto.*;
import com.ssbc.loanportal.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Boolean> refreshTokenBlacklist = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void sendOtp(SendOtpRequest request) {
        String destination = request.getEmail() != null ? request.getEmail() : request.getMobile();
        if (destination == null) {
            throw new IllegalArgumentException("Email or mobile is required");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        OtpEntry entry = new OtpEntry(otp, Instant.now().plusSeconds(300)); // 5 min expiry
        otpStore.put(destination, entry);

        // TODO: integrate with email/SMS provider
        System.out.println("DEBUG OTP for " + destination + " = " + otp);
    }


    @Override
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        OtpEntry entry = otpStore.get(request.getEmailOrMobile());
        if (entry == null || entry.getExpiry().isBefore(Instant.now()) || !entry.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // remove OTP once used
        otpStore.remove(request.getEmailOrMobile());

        // ---------- IMPORTANT PART ----------
        // Load OR create user
        User user = userRepository.findByEmail(request.getEmailOrMobile())
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(request.getEmailOrMobile());
                    u.setFullName("New User");
                    u.setRoles("USER");
                    return userRepository.save(u);
                });
        // -----------------------------------

        // generate tokens for this user
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }



    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO: integrate with User entity + password hashing
        // For now: simple demo user
        if (!"demo@ssbc.com".equalsIgnoreCase(request.getEmail()) ||
                !"Password@123".equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(request.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(request.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        if (Boolean.TRUE.equals(refreshTokenBlacklist.get(token))) {
            throw new RuntimeException("Refresh token revoked");
        }

        String subject = jwtTokenProvider.getSubject(token);
        String newAccess = jwtTokenProvider.generateAccessToken(subject);
        String newRefresh = jwtTokenProvider.generateRefreshToken(subject);

        return new AuthResponse(newAccess, newRefresh);
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenBlacklist.put(request.getRefreshToken(), true);
    }

    private static class OtpEntry {
        private final String otp;
        private final Instant expiry;

        public OtpEntry(String otp, Instant expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getExpiry() {
            return expiry;
        }
    }
}
