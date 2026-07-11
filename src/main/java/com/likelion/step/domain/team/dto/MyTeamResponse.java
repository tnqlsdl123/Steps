package com.likelion.step.domain.team.dto;

public record MyTeamResponse(
    Long teamId,
    Long postId,
    String postStatus,
    String title,
    String writerName,
    String createdAt
) {}