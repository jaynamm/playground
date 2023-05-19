package com.encore.playground.domain.member.dto;

import com.encore.playground.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String userid;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String curriculum;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    /**
     * Member to MemberDTO 변환
     * @param member
     */

    public MemberDto(Member member) {
        this.id = member.getId();
        this.userid = member.getUserid();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.curriculum = member.getCurriculum();
        this.createdDate = member.getCreatedDate();
        this.modifiedDate = member.getModifiedDate();
    }

    public Member toMember(){
        return Member.builder()
                .id(id)
                .userid(userid)
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .curriculum(curriculum)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}
