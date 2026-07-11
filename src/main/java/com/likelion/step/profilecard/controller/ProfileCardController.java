package com.likelion.step.profilecard.controller;


import com.likelion.step.profilecard.dto.ProfileCardCreateRequest;
import com.likelion.step.profilecard.service.ProfileCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")

public class ProfileCardController {

    private final ProfileCardService profileCardService;

    @PostMapping
    public void createProfileCard(
            @PathVariable Long memberId,
            @RequestBody ProfileCardCreateRequest request){
        profileCardService.createProfileCard(memberId, request);
    }
}
