package com.likelion.step.profilecard.controller;


import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.profilecard.dto.ProfileCardResponse;
import com.likelion.step.profilecard.dto.ProfileCardUpdateRequest;
import com.likelion.step.profilecard.service.ProfileCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")

public class ProfileCardController {

    private final ProfileCardService profileCardService;

    @PatchMapping
    public ApiResponse<ProfileCardResponse> updateProfileCard(
            @RequestBody ProfileCardUpdateRequest request
    ) {

        Long userId = 1L; // 유저 기능 구현 뒤 수정

        ProfileCardResponse response = profileCardService.updateProfileCard(userId, request);

        return ApiResponse.success(response);
    }
}
