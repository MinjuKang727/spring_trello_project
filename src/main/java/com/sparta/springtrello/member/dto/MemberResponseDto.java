package com.sparta.springtrello.member.dto;


import com.sparta.springtrello.member.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

//    private Long userId;
    private Long workspaceId;
    private MemberRole memberRole;

}
