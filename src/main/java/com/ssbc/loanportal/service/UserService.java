package com.ssbc.loanportal.service;

import com.ssbc.loanportal.dto.UserProfileResponse;
import com.ssbc.loanportal.dto.UserUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserProfileResponse getCurrentUserProfile();

    UserProfileResponse updateCurrentUserProfile(UserUpdateRequest request);
}
