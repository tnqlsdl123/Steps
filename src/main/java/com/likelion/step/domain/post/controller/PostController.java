package com.likelion.step.domain.post.controller;

import com.likelion.step.domain.post.dto.PostCreateRequest;
import com.likelion.step.domain.post.dto.PostCreateResponse;
import com.likelion.step.domain.post.service.PostService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ApiResponse<PostCreateResponse> create(
      @LoginMember Long memberId,
      @RequestBody PostCreateRequest request) {

    PostCreateResponse response = postService.create(memberId, request);
    return ApiResponse.success("모집글이 등록되었습니다.", response);
  }
}
