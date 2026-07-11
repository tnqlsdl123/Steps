package com.likelion.step.domain.post.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
  INVALID_TITLE("POST_400", "제목은 1자 이상 30자 이내로 입력해주세요.", HttpStatus.BAD_REQUEST),
  INVALID_CONTENT("POST_400", "내용은 1자 이상 200자 이내로 입력해주세요.", HttpStatus.BAD_REQUEST),
  INVALID_RECRUIT_COUNT("POST_400", "모집 인원은 1명 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
  INVALID_DEADLINE("POST_400", "모집 마감일 형식이 올바르지 않습니다. (YYYY-MM-DD)", HttpStatus.BAD_REQUEST),
  INVALID_CATEGORY("POST_400", "유효하지 않은 모집 분야입니다.", HttpStatus.BAD_REQUEST),
  INVALID_ACTIVITY_TYPE("POST_400", "유효하지 않은 활동 방식입니다.", HttpStatus.BAD_REQUEST),
  INVALID_INPUT("POST_400", "필수 입력값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
  AUTHOR_NOT_FOUND("POST_401", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
