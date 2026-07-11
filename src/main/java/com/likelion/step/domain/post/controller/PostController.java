package com.likelion.step.domain.post.controller;

import com.likelion.step.domain.post.dto.PostApplyResponse;
import com.likelion.step.domain.post.dto.PostCreateRequest;
import com.likelion.step.domain.post.dto.PostCreateResponse;
import com.likelion.step.domain.post.dto.PostDetailResponse;
import com.likelion.step.domain.post.service.PostService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/{postId}")
  public ApiResponse<PostDetailResponse> getDetail(
      @LoginMember Long memberId,
      @PathVariable Long postId) {

    PostDetailResponse response = postService.getDetail(memberId, postId);
    return ApiResponse.success(response);
  }

  @PostMapping("/{postId}/apply")
  public ApiResponse<PostApplyResponse> apply(
      @LoginMember Long memberId,
      @PathVariable Long postId) {

    PostApplyResponse response = postService.apply(memberId, postId);
    return ApiResponse.success("지원이 완료되었습니다.", response);
  }
}
