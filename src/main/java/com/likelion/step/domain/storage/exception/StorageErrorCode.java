package com.likelion.step.domain.storage.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StorageErrorCode implements BaseErrorCode {
  INVALID_TYPE("STORAGE_400", "type은 applied 또는 posted만 가능합니다.", HttpStatus.BAD_REQUEST),
  INVALID_STATUS("STORAGE_400", "status는 all, recruiting, closed만 가능합니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}