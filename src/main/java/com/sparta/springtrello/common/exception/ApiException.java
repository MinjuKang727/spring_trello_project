package com.sparta.springtrello.common.exception;

import com.sparta.springtrello.common.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final BaseCode errorCode;
}
