package com.likelion.step.domain.team.dto;

import java.util.List;

public record TeamMemberResponse(
    Long memberId,
    String name,
    List<String> collaborationTags
) {}