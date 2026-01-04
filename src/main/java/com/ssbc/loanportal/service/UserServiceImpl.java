package com.ssbc.loanportal.service;
import com.ssbc.loanportal.entity.User;
import com.ssbc.loanportal.repository.UserRepository;
import com.ssbc.loanportal.dto.UserProfileResponse;
import com.ssbc.loanportal.dto.UserUpdateRequest;
import com.ssbc.loanportal.exception.ResourceNotFoundException;
import com.ssbc.loanportal.security.CurrentUser;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }


    @Override
    public UserProfileResponse getCurrentUserProfile() {
        String email = currentUser.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserProfileResponse updateCurrentUserProfile(UserUpdateRequest request) {
        String email = currentUser.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        user.setMobile(request.getMobile());
        user.setAddress(request.getAddress());
        userRepository.save(user);

        return mapToResponse(user);
    }

    private UserProfileResponse mapToResponse(User u) {
        UserProfileResponse r = new UserProfileResponse();
        r.setId(u.getId());
        r.setEmail(u.getEmail());
        r.setFullName(u.getFullName());
        r.setMobile(u.getMobile());
        r.setPan(u.getPan());
        r.setAadhar(u.getAadhar());
        r.setAddress(u.getAddress());
        return r;
    }

}
