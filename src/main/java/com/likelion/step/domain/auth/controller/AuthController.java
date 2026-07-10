package com.likelion.step.domain.auth.controller;

import com.likelion.step.domain.auth.dto.LoginRequest;
import com.likelion.step.domain.auth.dto.LoginResponse;
import com.likelion.step.domain.auth.service.AuthService;
import com.likelion.step.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = authService.login(request);
    return ApiResponse.success("로그인에 성공했습니다.", response);
  }
}