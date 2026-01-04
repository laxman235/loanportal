package com.ssbc.loanportal.controller;


import com.ssbc.loanportal.service.UserService;
import com.ssbc.loanportal.dto.ApiResponse;
import com.ssbc.loanportal.dto.UserProfileResponse;
import com.ssbc.loanportal.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMe() {
        return ResponseEntity.ok(
                ApiResponse.ok("Profile fetched", userService.getCurrentUserProfile())
        );
    }

////    @PutMapping("/me")
////    public ResponseEntity<ApiResponse<UserProfileResponse>> updateMe(
////            @Valid @RequestBody UserUpdateRequest request) {
////
////        return ResponseEntity.ok(
////                ApiResponse.ok("Profile updated", userService.updateCurrentUserProfile(request))
////        );
//    }

}
