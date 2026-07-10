package com.likelion.step.domain.auth.exception;

public class InvalidSignupException extends IllegalArgumentException {
  public InvalidSignupException(String message) {
    super(message);
  }
}