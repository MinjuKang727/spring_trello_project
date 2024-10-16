package com.sparta.springtrello.config;

import com.sparta.springtrello.common.ApiResponse;
import com.sparta.springtrello.common.ReasonDto;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.common.exception.InvalidRequestException;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<String>> invalidRequestExceptionException(InvalidRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiResponse<String>> handleServerException(ServerException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getErrorResponse(status, ex.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<String>> handleApiException(ApiException ex) {
        ReasonDto status = ex.getErrorCode().getReasonHttpStatus();
        return getErrorResponse(status.getHttpStatus(), status.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthException(AuthException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return getErrorResponse(status, ex.getMessage());
    }

    public ResponseEntity<ApiResponse<String>> getErrorResponse(HttpStatus status, String message) {

        return new ResponseEntity<>(ApiResponse.createError(message, status.value()), status);
    }
}
