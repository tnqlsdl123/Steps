package com.likelion.step.domain.verification.dto;

import com.likelion.step.domain.verification.entity.VerificationStatus;

public record VerificationSubmitResponse(
    VerificationStatus verificationStatus
) {}
