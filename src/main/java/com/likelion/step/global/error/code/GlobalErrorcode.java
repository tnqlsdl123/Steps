package com.likelion.step.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorcode implements BaseErrorCode {

  INVALID_INPUT_VALUE("COMMON_400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
  INTERNAL_SERVER_ERROR("COMMON_500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  PROFILE_TAGS_COUNT_ERROR(
          "PROFILE_400",
          "협업 성향 키워드는 정확히 2개를 선택해야 합니다.",
          HttpStatus.BAD_REQUEST),
  MEMBER_NOT_FOUND(
          "MEMBER_404",
          "회원을 찾을 수 없습니다.",
          HttpStatus.NOT_FOUND
  );

  private final String code;
  private final String message;
  private final HttpStatus status;
}
