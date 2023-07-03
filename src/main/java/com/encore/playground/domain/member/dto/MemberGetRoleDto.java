package com.encore.playground.domain.member.dto;

import com.encore.playground.global.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberGetRoleDto {
    private String role;
}
