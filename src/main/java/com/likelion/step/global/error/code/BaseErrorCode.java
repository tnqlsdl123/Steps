package com.likelion.step.global.error.code;

import org.springframework.http.HttpStatus; // 11번째 줄에서 에러코드, 상태를 숫자로 전달하기 위한 클래스

public interface BaseErrorCode {

  String getCode();

  String getMessage();

  HttpStatus getStatus();
}
