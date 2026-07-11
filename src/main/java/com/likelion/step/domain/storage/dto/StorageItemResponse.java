package com.likelion.step.domain.storage.dto;

public record StorageItemResponse(
    Long postId,
    String status,      // "모집중" / "모집마감"
    String title,
    String writerName,
    String createdAt
) {}