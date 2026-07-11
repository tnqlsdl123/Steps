package com.likelion.step.domain.home.dto;

public record HomePostResponse(
    Long postId,
    String title,
    String category,
    String writerName,
    String contentPreview
) {}