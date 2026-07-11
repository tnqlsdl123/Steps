package com.likelion.step.domain.post.dto;

public record PostCreateRequest(
    String title,
    String applicationUrl,
    String category,        // "기획/아이디어" 또는 "PLANNING_IDEA"
    String recruitDeadline, // "2026-07-15"
    Integer recruitCount,
    String activityType,    // "비대면" 또는 "ONLINE"
    String activityPurpose,
    String content
) {}
