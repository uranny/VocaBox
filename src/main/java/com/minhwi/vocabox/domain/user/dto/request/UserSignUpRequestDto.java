package com.minhwi.vocabox.domain.user.dto.request;

import lombok.Data;

@Data
public class UserSignUpRequestDto {
    private String username;
    private String password;
}
