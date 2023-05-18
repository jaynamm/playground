package com.encore.playground.domain.member.dto;


import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchDto {
    private String userid;
    private String email;
}
