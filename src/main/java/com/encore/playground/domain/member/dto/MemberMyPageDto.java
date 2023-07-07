package com.encore.playground.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMyPageDto {
    private String userid;
    private String email;
    private String name;
    private String nickname;
    private String curriculum;
}
