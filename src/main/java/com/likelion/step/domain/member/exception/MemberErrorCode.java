package com.likelion.step.domain.member.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {
  INVALID_SIGNUP("MEMBER_400", "입력값 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  DUPLICATE_EMAIL("MEMBER_409", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
