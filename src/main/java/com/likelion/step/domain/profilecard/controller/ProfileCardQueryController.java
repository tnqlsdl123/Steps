package com.likelion.step.domain.profilecard.controller;

import com.likelion.step.domain.profilecard.dto.ProfileCardResponse;
import com.likelion.step.domain.profilecard.service.ProfileCardQueryService;
import com.likelion.step.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileCardQueryController {

    private final ProfileCardQueryService profileCardQueryService;

    // 프로필 카드 조회
    @GetMapping("/{memberId}")
    public ApiResponse<ProfileCardResponse> getProfileCard(
            @PathVariable("memberId") Long memberId
    ) {
        return ApiResponse.success(
                profileCardQueryService.getProfileCard(memberId)
        );
    }
}
