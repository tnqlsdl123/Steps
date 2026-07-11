package com.likelion.step.domain.verification.dto;

import java.time.LocalDateTime;

public record PendingVerificationResponse(
    Long verificationId,
    Long memberId,
    String name,
    String school,
    String fileUrl,
    LocalDateTime submittedAt
) {}