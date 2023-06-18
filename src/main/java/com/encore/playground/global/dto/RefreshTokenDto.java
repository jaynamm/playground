package com.encore.playground.global.dto;

import com.encore.playground.global.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    private Long id;
    private String refreshToken;
    private String memberId;


    /**
     * dto -> entity
     */
    public RefreshToken toEntity() {
        return RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();
    }

}

