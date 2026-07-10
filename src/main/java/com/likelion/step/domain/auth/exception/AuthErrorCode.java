package com.likelion.step.domain.auth.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // 1. 반드시 임포트 필요

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

  // 2. 생성자에 HttpStatus 인자를 추가합니다.
  INVALID_REQUEST("AUTH_400", "요청 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  LOGIN_FAILED("AUTH_401", "이메일 또는 비밀번호를 다시 확인해주세요.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status; // 3. 필드 추가

  // 4. 인터페이스의 약속(getStatus)을 지키기 위해 메서드 구현
  @Override
  public HttpStatus getStatus() {
    return this.status;
  }
}
