package com.likelion.step.domain.post.dto;

public record PostDetailResponse(
    Long postId,
    String title,
    String writerName,
    String createdAt,
    String category,
    String applicationUrl,
    String recruitDeadline,
    String recruitCount,
    String activityType,
    String activityPurpose,
    String content,
    String postStatus,
    boolean hasApplied
) {}