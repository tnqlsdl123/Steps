package com.likelion.step.domain.auth.dto;

public record SignupRequest(
    String name,
    String birthDate,
    String gender,
    String school,
    String major,
    Integer grade,
    String email,
    String password,
    String passwordCheck
) {}