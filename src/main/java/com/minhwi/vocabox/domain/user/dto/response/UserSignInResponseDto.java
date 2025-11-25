package com.minhwi.vocabox.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInResponseDto {
    private String accessToken;
}
