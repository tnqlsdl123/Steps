package com.likelion.step.domain.application.dto;

public record ApplicationDecisionResponse(
    Long teamId,
    String contactEmail,
    boolean isPostClosed
) {}