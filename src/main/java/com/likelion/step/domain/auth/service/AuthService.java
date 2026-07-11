package com.likelion.step.domain.auth.service;

import com.likelion.step.domain.auth.dto.LoginRequest;
import com.likelion.step.domain.auth.dto.LoginResponse;
import com.likelion.step.domain.auth.entity.GeneralLogin;
import com.likelion.step.domain.auth.exception.InvalidCredentialsException;
import com.likelion.step.domain.auth.repository.GeneralLoginRepository;
import com.likelion.step.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final GeneralLoginRepository generalLoginRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public LoginResponse login(LoginRequest request) {
    GeneralLogin generalLogin = generalLoginRepository.findByEmail(request.email())
        .orElseThrow(() -> new InvalidCredentialsException("이메일 또는 비밀번호를 다시 확인해주세요."));

    if (!passwordEncoder.matches(request.password(), generalLogin.getPassword())) {
      throw new InvalidCredentialsException("이메일 또는 비밀번호를 다시 확인해주세요.");
    }

    Long memberId = generalLogin.getMember().getMemberId();
    String accessToken = jwtProvider.createToken(memberId, request.autoLogin());

    // TODO: 학교 인증 여부는 별도 테이블에서 조회 (지금은 임시로 true 고정)
    boolean isSchoolVerified = true;

    return new LoginResponse(accessToken, isSchoolVerified);
  }
}