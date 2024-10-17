package com.sparta.springtrello.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    BAD_REQUEST_EMPTY_TITLE(HttpStatus.BAD_REQUEST, 400, "제목이 비어 있습니다."),

    //예외 예시
    BAD_REQUEST_UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,400,"지원되지 않는 JWT 토큰입니다."),
    BAD_REQUEST_ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST,400,"잘못된 JWT 토큰입니다."),
    UNAUTHORIZED_INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401,"유효하지 않는 JWT 서명입니다."),
    UNAUTHORIZED_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,401,"만료된 JWT 토큰입니다."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,401,"JWT 토큰 검증 중 오류가 발생했습니다."),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, 403, "관리자 권한이 없습니다."),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, 404, "JWT 토큰이 필요합니다."),


    //워크스페이스 관련 예외
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 404, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_WORKSPACE(HttpStatus.NOT_FOUND, 404, "해당 워크스페이스를 찾을 수 없습니다."),
    FORBIDDEN_NOT_MEMBER(HttpStatus.FORBIDDEN,403,"해당 워크스페이스에 접근할 수 있는 권한이 없습니다."),
    FORBIDDEN_ACCESS_INVITE(HttpStatus.FORBIDDEN, 403, "초대 권한이 없습니다."),
    FORBIDDEN_ACCESS_CHANGE_ROLE(HttpStatus.FORBIDDEN, 403, "해당 워크스페이스의 관리자가 아닙니다."),
    CONFLICT_MEMBER(HttpStatus.BAD_REQUEST, 400, "해당 유저는 이미 초대된 멤버입니다."),
    BAD_REQUEST_NOT_MEMBER(HttpStatus.BAD_REQUEST,404,"추가하려는 유저는 해당 워크스페이스의 멤버가 아닙니다."),
    BAD_REQUEST_INVALID_WORKSPACE_ID(HttpStatus.BAD_REQUEST,400,"워크스페이스 ID가 잘못 입력되었습니다."),

    // 보드 관련 예외
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, 404, "해당 보드를 찾을 수 없습니다."),

    // 덱 관련 예외
    NOT_FOUND_DECK(HttpStatus.NOT_FOUND, 404, "해당 덱을 찾을 수 없습니다."),
    NOT_MOVED_DECK(HttpStatus.BAD_REQUEST, 400, "덱이 이동하지 않았습니다."),
    NO_DECK_IN_BOARD(HttpStatus.BAD_REQUEST, 400, "해당 보드에 덱이 존재하지 않습니다."),

    //카드 관련 예외
    FORBIDDEN_READ_ONLY(HttpStatus.FORBIDDEN,403,"현재 권한이 읽기 전용입니다."),
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND,404,"해당 카드를 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, 404, "해당 댓글을 찾을 수 없습니다."),

    //파일 관련 예외
    INTERNAL_SERVER_ERROR_FAILED_CONVERT_FILE(HttpStatus.INTERNAL_SERVER_ERROR, 500, "파일 변환에 문제가 생겼습니다."),
    BAD_REQUEST_EXCEED_FILE_SIZE(HttpStatus.BAD_REQUEST,400,"파일 크기는 5MB를 넘을 수 없습니다."),
    BAD_REQUEST_INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST,400,"지원되지 않는 파일입니다. 파일은 jpg,png,pdf,csv만 업로드할 수 있습니다."),
    BAD_REQUEST_INVALID_FILE_NAME(HttpStatus.BAD_REQUEST,400,"파일명이 유효하지 않습니다."),

    //매니저 관련 예외
    NOT_FOUND_MANAGER(HttpStatus.NOT_FOUND,404,"해당 매니저를 찾을 수 없습니다."),


    //멤버 관련 예외
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,404,"해당 멤버를 찾을 수 없습니다."),
    FORBIDDEN_NOT_MANAGER(HttpStatus.FORBIDDEN,403,"현재 요청 멤버가 카드의 매니저가 아닙니다."),
    BAD_REQUEST_NOT_MANAGER(HttpStatus.BAD_REQUEST,400,"해당 멤버는 카드의 매니저가 아닙니다."),

    //이메일 인증 관련 예외
    FAIL_EMAIL_SENDING(HttpStatus.INTERNAL_SERVER_ERROR, 500, "이메일 전송에 실패했습니다."),
    SEND_AUTH_EMAIL(HttpStatus.OK, 200, "메일이 전송되었습니다. 인증번호와 함께 다시 요청을 보내주십시오."),
    FAIL_EMAIL_AUTHENTICATION(HttpStatus.FORBIDDEN, 403, "인증번호가 일치하지 않습니다."),
    EMAIL_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, 400, "이미 사용 중인 이메일입니다."),

    // 인증 정보 관련 예외
    NOT_FOUND_AUTHENTICATION(HttpStatus.FORBIDDEN, 403, "인증 정보를 찾을 수 없습니다."),

    // 슬랙 관련 예외
    UNAUTHORIZED_INVALID_STATE(HttpStatus.UNAUTHORIZED, 401, "유효하지 않은 State 입니다."),
    SLACK_ACCESSTOKEN_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "엑세스 토큰 요청 중, 문제가 발생하였습니다."),
    SLACK_FETCH_USER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "유저 목록을 가져오던 중, 문제가 발생하였습니다."),
    SLACK_SEND_MESSAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "슬랙 메시지 전송 실패"),
    BAD_REQUEST_CONTENT_CATEGORY(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 컨텐츠 카테고리입니다."),
    NOT_SLACK_USER(HttpStatus.BAD_REQUEST, 400, "슬랙 유저 정보가 존재하지 않아 알람을 보낼 수 없습니다."),
    SLACK_CREATE_CHANNEL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "워크스페이스 알람용 채널 생성에 실패하였습니다."),
    SLACK_INVITE_MEMBER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "슬랙 채널 초대 실패"),
    SLACK_DM_CHANNEL_CONNECTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "슬랙 DM 채널 연결 실패"),

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
