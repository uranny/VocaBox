package com.minhwi.vocabox.domain.user.service;

import com.minhwi.vocabox.domain.user.dto.request.UserSignInRequestDto;
import com.minhwi.vocabox.domain.user.dto.request.UserSignUpRequestDto;
import com.minhwi.vocabox.domain.user.dto.response.UserSignInResponseDto;

public interface UserService {
    void signUp(UserSignUpRequestDto signUpDto);
    UserSignInResponseDto signIn(UserSignInRequestDto signInDto);
}
