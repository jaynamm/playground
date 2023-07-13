package com.encore.playground.domain.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterForm {
    @NotEmpty(message = "아이디를 입력해주세요.")
    @Size(max = 15)
    private String userid;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "패스워드를 입력해주세요.")
    private String password;

    @NotEmpty(message = "패스워드를 한 번 더 입력해주세요.")
    private String passwordCheck;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Size(min = 3, max = 15)
    @NotEmpty(message = "최소 3자 이상 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "교육과정을 입력해주세요.")
    private String curriculum;
}
