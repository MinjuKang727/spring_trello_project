package com.sparta.springtrello.domain.notification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationMessage {
    // 워크스페이스 관련 메세지
    WORKSPACE_CREATED("새로운 워크스페이스가 생성되었습니다."),
    WORKSPACE_UPDATED("워크스페이스가 수정되었습니다."),
    WORKSPACE_DELETED("워크스페이스가 삭제되었습니다."),
    WORKSPACE_JOIN_NEW_MEMBER("워크스페이스에 새로운 멤버가 초대되었습니다."),
    WORKSPACE_INVITE_NEW_MEMBER("워크스페이스에 새로운 멤버를 초대하였습니다."),

    // 멤버
    MEMBER_ROLE_CHANGED("멤버 역할이 변경되었습니다."),

    // 카드
    CARD_CREATED("새로운 카드가 추가되었습니다."),
    CARD_UPDATED("카드 내용이 변경되었습니다."),
    CARD_DELETED("카드가 삭제되었습니다."),
    CARD_MANAGER_ADDED("새로운 카드 담당자가 추가되었습니다."),
    CARD_MANAGEER_DELETED("카드 담당자가 삭제되었습니다."),

    // 댓글
    COMMENT_CREATED("카드에 새로운 댓글이 등록되었습니다."),
    COMMENT_UPDATED("댓글 내용이 변경되었습니다."),
    COMMENT_DELETED("댓글이 삭제되었습니다.")

    ;


    private final String message;

}
