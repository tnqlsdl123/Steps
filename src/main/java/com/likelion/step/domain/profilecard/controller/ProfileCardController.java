package com.likelion.step.domain.profilecard.controller;

import com.likelion.step.domain.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.domain.profilecard.service.ProfileCardService;
import com.likelion.step.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileCardController {

    private final ProfileCardService profileCardService;

    @PostMapping("/{memberId}")
    public ApiResponse<Void> createProfileCard(
            @PathVariable("memberId") Long memberId,
            @RequestBody ProfileCardCreateRequest request
    ) {
        profileCardService.createProfileCard(memberId, request);
        return ApiResponse.success();
    }
}