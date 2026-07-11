package com.likelion.step.domain.home.dto;

import java.util.List;

public record HomeResponse(
    List<String> categories,
    List<HomePostResponse> recentPosts
) {}