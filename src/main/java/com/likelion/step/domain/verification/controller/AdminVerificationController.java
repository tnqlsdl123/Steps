package com.likelion.step.domain.verification.controller;

import com.likelion.step.domain.verification.dto.PendingVerificationResponse;
import com.likelion.step.domain.verification.service.AdminVerificationService;
import com.likelion.step.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/school-verification")
public class AdminVerificationController {

  private final AdminVerificationService adminVerificationService;

  @GetMapping("/pending")
  public ApiResponse<List<PendingVerificationResponse>> getPendingList() {
    return ApiResponse.success(adminVerificationService.getPendingList());
  }

  @PatchMapping("/{verificationId}/approve")
  public ApiResponse<Void> approve(@PathVariable Long verificationId) {
    adminVerificationService.approve(verificationId);
    return ApiResponse.success();
  }

  @PatchMapping("/{verificationId}/reject")
  public ApiResponse<Void> reject(@PathVariable Long verificationId) {
    adminVerificationService.reject(verificationId);
    return ApiResponse.success();
  }
}