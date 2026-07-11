package com.likelion.step.domain.application.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements BaseErrorCode {
  ALREADY_APPLIED("APPLICATION_403", "이미 지원한 모집글입니다.", HttpStatus.FORBIDDEN),
  SELF_APPLY_NOT_ALLOWED("APPLICATION_403", "본인이 작성한 모집글에는 지원할 수 없습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}