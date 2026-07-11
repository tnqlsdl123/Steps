package com.likelion.step.global.error.handler;

import com.likelion.step.domain.auth.exception.AuthErrorCode;
import com.likelion.step.domain.auth.exception.DuplicateEmailException;
import com.likelion.step.domain.auth.exception.InvalidCredentialsException;
import com.likelion.step.domain.auth.exception.InvalidSignupException;
import com.likelion.step.domain.member.exception.MemberErrorCode;
import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralException;
import com.likelion.step.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ApiResponse<Void>> handleBaseException(GeneralException e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(ApiResponse.fail(e.getErrorCode()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
    FieldError fieldError = e.getBindingResult().getFieldError();
    String message = (fieldError == null)
        ? GlobalErrorcode.INVALID_INPUT_VALUE.getMessage()
        : fieldError.getDefaultMessage();
    return ResponseEntity
        .status(GlobalErrorcode.INVALID_INPUT_VALUE.getStatus())
        .body(ApiResponse.failWithMessage(GlobalErrorcode.INVALID_INPUT_VALUE, message));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException e) {
    return ResponseEntity
        .status(AuthErrorCode.LOGIN_FAILED.getStatus())
        .body(ApiResponse.fail(AuthErrorCode.LOGIN_FAILED));
  }

  @ExceptionHandler(InvalidSignupException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidSignup(InvalidSignupException e) {
    return ResponseEntity
        .status(MemberErrorCode.INVALID_SIGNUP.getStatus())
        .body(ApiResponse.failWithMessage(MemberErrorCode.INVALID_SIGNUP, e.getMessage()));
  }

  @ExceptionHandler(DuplicateEmailException.class)
  public ResponseEntity<ApiResponse<Void>> handleDuplicateEmail(DuplicateEmailException e) {
    return ResponseEntity
        .status(MemberErrorCode.DUPLICATE_EMAIL.getStatus())
        .body(ApiResponse.failWithMessage(MemberErrorCode.DUPLICATE_EMAIL, e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
    log.error("처리되지 않은 예외 발생", e);
    return ResponseEntity
        .status(GlobalErrorcode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.fail(GlobalErrorcode.INTERNAL_SERVER_ERROR));
  }
}
