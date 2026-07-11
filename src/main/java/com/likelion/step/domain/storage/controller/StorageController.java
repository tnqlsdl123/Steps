package com.likelion.step.domain.storage.controller;

import com.likelion.step.domain.storage.dto.StorageItemResponse;
import com.likelion.step.domain.storage.service.StorageService;
import com.likelion.step.global.response.ApiResponse;
import com.likelion.step.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageController {

  private final StorageService storageService;

  @GetMapping
  public ApiResponse<List<StorageItemResponse>> getList(
      @LoginMember Long memberId,
      @RequestParam String type,
      @RequestParam(defaultValue = "all") String status) {

    List<StorageItemResponse> response = storageService.getList(memberId, type, status);
    return ApiResponse.success(response);
  }
}