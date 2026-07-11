package com.likelion.step.domain.auth.dto;

public record LoginRequest(
    String email,
    String password,
    boolean autoLogin
) {}