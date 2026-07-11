package com.likelion.step.domain.team.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TeamErrorCode implements BaseErrorCode {
  TEAM_NOT_FOUND("TEAM_404", "팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOT_TEAM_MEMBER("TEAM_403", "해당 팀의 멤버가 아닙니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}