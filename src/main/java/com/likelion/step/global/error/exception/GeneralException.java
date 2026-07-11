package com.likelion.step.global.error.exception;

import com.likelion.step.global.error.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

  private final BaseErrorCode errorCode;

  public GeneralException(BaseErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
