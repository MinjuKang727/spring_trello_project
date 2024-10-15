package com.sparta.springtrello.domain.member.dto;


import com.sparta.springtrello.domain.member.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private MemberRole memberRole;

}
