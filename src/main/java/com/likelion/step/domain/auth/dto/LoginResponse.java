package com.likelion.step.domain.auth.dto;

public record LoginResponse(
    String accessToken,
    boolean isSchoolVerified
) {}