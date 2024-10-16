package com.sparta.springtrello.domain.member.dto;


import com.sparta.springtrello.domain.member.enums.InvitationStatus;
import com.sparta.springtrello.domain.member.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

//    private Long userId;
    private Long workspaceId;
    private InvitationStatus invitationStatus;
    private MemberRole memberRole;

}
