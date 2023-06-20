package com.encore.playground.domain.member.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordDto {
    private String userid;
    private String password;
}
