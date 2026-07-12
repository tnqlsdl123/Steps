package com.likelion.step.domain.profilecard.controller;

import com.likelion.step.domain.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.domain.profilecard.dto.ProfileCardCreateResponse;
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
    public ApiResponse<ProfileCardCreateResponse> createProfileCard(
        @LoginMember Long memberId,
        @RequestBody ProfileCardCreateRequest request) {

        ProfileCardCreateResponse response = profileCardService.createProfileCard(memberId, request);
        return ApiResponse.success("프로필 카드가 생성되었습니다.", response);
    }

    // 프로필 카드 수정
    @PatchMapping
    public ApiResponse<ProfileCardResponse> updateProfileCard(
        @LoginMember Long memberId,
        @RequestBody ProfileCardUpdateRequest request) {

        ProfileCardResponse response = profileCardService.updateProfileCard(memberId, request);
        return ApiResponse.success(response);
    }

    // 내 프로필 카드 조회 (로그인한 본인) ← 추가
    @GetMapping("/me")
    public ApiResponse<ProfileCardResponse> getMyProfileCard(
        @LoginMember Long memberId) {

        return ApiResponse.success(profileCardQueryService.getProfileCard(memberId));
    }

    // 특정 회원 프로필 카드 조회
    @GetMapping("/{memberId}")
    public ApiResponse<ProfileCardResponse> getProfileCard(
        @PathVariable Long memberId) {

        return ApiResponse.success(profileCardQueryService.getProfileCard(memberId));
    }
}