package com.likelion.step.domain.auth.exception;

public class DuplicateEmailException extends IllegalArgumentException {
  public DuplicateEmailException(String message) {
    super(message);
  }
}
