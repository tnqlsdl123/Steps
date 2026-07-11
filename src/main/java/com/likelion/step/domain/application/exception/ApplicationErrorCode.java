package com.likelion.step.domain.application.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements BaseErrorCode {
  ALREADY_APPLIED("APPLICATION_403", "이미 지원한 모집글입니다.", HttpStatus.FORBIDDEN),
  SELF_APPLY_NOT_ALLOWED("APPLICATION_403", "본인이 작성한 모집글에는 지원할 수 없습니다.", HttpStatus.FORBIDDEN),
  APPLICATION_NOT_FOUND("APPLICATION_404", "지원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),     // 추가
  NOT_APPLICATION_OWNER("APPLICATION_403", "모집글 작성자만 수락/거절할 수 있습니다.", HttpStatus.FORBIDDEN), // 추가
  INVALID_DECISION("APPLICATION_400", "decision은 ACCEPT 또는 REJECT만 가능합니다.", HttpStatus.BAD_REQUEST); // 추가

  private final String code;
  private final String message;
  private final HttpStatus status;
}