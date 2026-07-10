package com.likelion.step.domain.verification.controller;

import com.likelion.step.domain.verification.dto.VerificationStatusResponse;
import com.likelion.step.domain.verification.dto.VerificationSubmitResponse;
import com.likelion.step.domain.verification.service.SchoolVerificationService;
import com.likelion.step.global.security.LoginMember;
import com.likelion.step.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/school-verification")
public class SchoolVerificationController {

  private final SchoolVerificationService verificationService;

  @PostMapping(consumes = "multipart/form-data")
  public ApiResponse<VerificationSubmitResponse> submit(
      @LoginMember Long memberId,
      @RequestPart("file") MultipartFile file) {

    VerificationSubmitResponse response = verificationService.submit(memberId, file);
    return ApiResponse.success(
        "인증 서류가 제출되었습니다. 승인까지 최대 12시간이 소요됩니다.", response);
  }

  @GetMapping("/status")
  public ApiResponse<VerificationStatusResponse> getStatus(@LoginMember Long memberId) {
    VerificationStatusResponse response = verificationService.getStatus(memberId);
    return ApiResponse.success(response);
  }
}
