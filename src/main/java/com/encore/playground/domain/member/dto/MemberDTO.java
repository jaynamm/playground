package com.encore.playground.domain.member.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String userid;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String curriculum;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
