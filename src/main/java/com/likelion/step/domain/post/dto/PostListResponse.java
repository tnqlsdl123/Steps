package com.likelion.step.domain.post.dto;

public record PostListResponse(
    Long postId,
    String title,
    String category,
    String writerName,
    String createdAt,
    String postStatus
) {}