package com.likelion.step.domain.verification.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VerificationErrorCode implements BaseErrorCode {
  EMPTY_FILE("VERIFY_400", "인증 서류 파일이 비어 있습니다.", HttpStatus.BAD_REQUEST),
  INVALID_FILE_TYPE("VERIFY_400", "이미지 파일(jpg, jpeg, png)만 업로드할 수 있습니다.", HttpStatus.BAD_REQUEST),
  FILE_STORE_FAILED("VERIFY_500", "파일 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  UNAUTHORIZED("VERIFY_401", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
  VERIFICATION_NOT_FOUND("VERIFY_404", "제출된 인증 서류가 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
