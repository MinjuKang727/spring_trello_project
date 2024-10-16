package com.sparta.springtrello.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    //예외 예시
    _BAD_REQUEST_UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,400,"지원되지 않는 JWT 토큰입니다."),
    _BAD_REQUEST_ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST,400,"잘못된 JWT 토큰입니다."),
    _UNAUTHORIZED_INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401,"유효하지 않는 JWT 서명입니다."),
    _UNAUTHORIZED_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,401,"만료된 JWT 토큰입니다."),
    _UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,401,"JWT 토큰 검증 중 오류가 발생했습니다."),
    _FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, 403, "관리자 권한이 없습니다."),
    _NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, 404, "JWT 토큰이 필요합니다."),


    //워크스페이스 관련 예외
    _NOT_FOUND_USER(HttpStatus.NOT_FOUND, 404, "해당 유저를 찾을 수 없습니다."),
    _NOT_FOUND_WORKSPACE(HttpStatus.NOT_FOUND, 404, "해당 워크스페이스를 찾을 수 없습니다."),
    _NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, 404, "해당 멤버를 찾을 수 없습니다."),
    _FORBIDDEN_NOT_MEMBER(HttpStatus.FORBIDDEN,403,"해당 워크스페이스에 접근할 수 있는 권한이 없습니다."),
    _FORBIDDEN_ACCESS_INVITE(HttpStatus.FORBIDDEN, 403, "초대 권한이 없습니다."),
    _FORBIDDEN_ACCESS_CHANGE_ROLE(HttpStatus.FORBIDDEN, 403, "해당 워크스페이스의 관리자가 아닙니다."),
    _CONFLICT_MEMBER(HttpStatus.BAD_REQUEST, 400, "해당 유저는 이미 초대된 멤버입니다."),

    //카드 관련 예외
    _FORBIDDEN_READ_ONLY(HttpStatus.FORBIDDEN,403,"현재 권한이 읽기 전용입니다."),
    _NOT_FOUND_CARD(HttpStatus.NOT_FOUND,404,"해당 카드를 찾을 수 없습니다."),

    _TEST_ERROR(HttpStatus.BAD_REQUEST, 400, "ApiException 예외 처리 테스트");


    private final HttpStatus httpStatus;
    private final Integer statusCode;
    private final String message;

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .statusCode(statusCode)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }
}
