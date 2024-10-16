package com.sparta.springtrello.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    //예외 예시
    BAD_REQUEST_UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,400,"지원되지 않는 JWT 토큰입니다."),
    BAD_REQUEST_ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST,400,"잘못된 JWT 토큰입니다."),
    UNAUTHORIZED_INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401,"유효하지 않는 JWT 서명입니다."),
    UNAUTHORIZED_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,401,"만료된 JWT 토큰입니다."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,401,"JWT 토큰 검증 중 오류가 발생했습니다."),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, 403, "관리자 권한이 없습니다."),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, 404, "JWT 토큰이 필요합니다."),

    //리스트 관련 예외
    NOT_FOUND_LIST(HttpStatus.NOT_FOUND,404,"해당 리스트를 찾을 수 없습니다."),

    //워크스페이스 관련 예외
    FORBIDDEN_NOT_MEMBER(HttpStatus.FORBIDDEN,403,"해당 워크스페이스에 접근할 수 있는 권한이 없습니다."),
    BAD_REQUEST_NOT_MEMBER(HttpStatus.BAD_REQUEST,404,"추가하려는 유저는 해당 워크스페이스의 멤버가 아닙니다."),

    //카드 관련 예외
    FORBIDDEN_READ_ONLY(HttpStatus.FORBIDDEN,403,"현재 권한이 읽기 전용입니다."),
    FORBIDDEN_NOT_MANAGER(HttpStatus.FORBIDDEN,403,"현재 요청 멤버가 카드의 매니저가 아닙니다."),
    BAD_REQUEST_NOT_MANAGER(HttpStatus.BAD_REQUEST,400,"해당 멤버는 카드의 매니저가 아닙니다."),
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND,404,"해당 카드를 찾을 수 없습니다."),

    //멤버 관련 예외
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,404,"해당 멤버를 찾을 수 없습니다."),

    TEST_ERROR(HttpStatus.BAD_REQUEST, 400, "ApiException 예외 처리 테스트");


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
