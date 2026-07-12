package com.likelion.step.domain.auth.dto;

public record SignupResponse(
    Long memberId,
    String accessToken  // 추가
) {}