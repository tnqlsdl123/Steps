package com.likelion.step.domain.post.dto;

import java.util.List;

public record ApplicantProfileResponse(
    Long applicationId,
    Long memberId,
    String name,
    String major,
    String school,
    Integer grade,
    String gender,
    List<String> collaborationTags,
    List<String> certificates,
    String selfIntroduction,
    String contactEmail
) {}