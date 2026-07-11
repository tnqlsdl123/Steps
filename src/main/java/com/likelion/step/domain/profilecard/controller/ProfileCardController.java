package com.likelion.step.domain.profilecard.controller;

import com.likelion.step.domain.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.domain.profilecard.dto.ProfileCardResponse;
import com.likelion.step.domain.profilecard.dto.ProfileCardUpdateRequest;
import com.likelion.step.domain.profilecard.service.ProfileCardService;
import com.likelion.step.domain.profilecard.service.ProfileCardQueryService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileCardController {

    private final ProfileCardService profileCardService;
    private final ProfileCardQueryService profileCardQueryService;

    // 프로필 카드 생성
    @PostMapping
    public ApiResponse<Void> createProfileCard(
        @LoginMember Long memberId,
        @RequestBody ProfileCardCreateRequest request) {

        profileCardService.createProfileCard(memberId, request);
        return ApiResponse.success();
    }

    // 프로필 카드 수정
    @PatchMapping
    public ApiResponse<ProfileCardResponse> updateProfileCard(
        @LoginMember Long memberId,
        @RequestBody ProfileCardUpdateRequest request) {

        ProfileCardResponse response = profileCardService.updateProfileCard(memberId, request);
        return ApiResponse.success(response);
    }

}