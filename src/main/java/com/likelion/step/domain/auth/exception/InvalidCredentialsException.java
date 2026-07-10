package com.likelion.step.domain.auth.exception;

public class InvalidCredentialsException extends IllegalArgumentException {
  public InvalidCredentialsException(String message) {
    super(message);
  }
}