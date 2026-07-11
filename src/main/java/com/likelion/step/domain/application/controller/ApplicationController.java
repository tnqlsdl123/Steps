package com.likelion.step.domain.application.controller;

import com.likelion.step.domain.application.dto.ApplicationDecisionRequest;
import com.likelion.step.domain.application.dto.ApplicationDecisionResponse;
import com.likelion.step.domain.application.service.ApplicationService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  @PatchMapping("/{applicationId}")
  public ApiResponse<ApplicationDecisionResponse> decide(
      @LoginMember Long memberId,
      @PathVariable Long applicationId,
      @RequestBody ApplicationDecisionRequest request) {

    ApplicationDecisionResponse response =
        applicationService.decide(memberId, applicationId, request);

    String message = "ACCEPT".equals(request.decision())
        ? "수락이 완료되었습니다."
        : "거절이 완료되었습니다.";

    return ApiResponse.success(message, response);
  }
}