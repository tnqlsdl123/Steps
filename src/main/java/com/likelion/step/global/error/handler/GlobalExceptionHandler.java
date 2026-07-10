package com.likelion.step.global.error.handler;

import com.likelion.step.global.error.code.GlobalErrorcode;
import com.likelion.step.global.error.exception.GeneralExeption;
import com.likelion.step.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralExeption.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(GeneralExeption exception) {
      return ResponseEntity
          .status(exception.getErrorCode().getStatus())
          .body(ApiResponse.fail(exception.getErrorCode()));
    }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException exception) {
    FieldError fieldError = exception.getBindingResult().getFieldError();
    String message = fieldError == null ? GlobalErrorcode.INVALID_INPUT_VALUE.getMessage() : fieldError.getDefaultMessage();

    return ResponseEntity
        .status(GlobalErrorcode.INVALID_INPUT_VALUE.getStatus())
        // 2. new ApiResponse 대신 이미 잘 만들어 두신 정적 팩토리 메서드를 호출합니다 ✨
        .body(ApiResponse.fail(GlobalErrorcode.INVALID_INPUT_VALUE, message));
  }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
      return ResponseEntity
          .status(GlobalErrorcode.INTERNAL_SERVER_ERROR.getStatus())
          .body(ApiResponse.fail(GlobalErrorcode.INTERNAL_SERVER_ERROR));
    }
}
