package com.minhwi.vocabox.domain.user.controller;

import com.minhwi.vocabox.domain.user.dto.request.UserSignInRequestDto;
import com.minhwi.vocabox.domain.user.dto.request.UserSignUpRequestDto;
import com.minhwi.vocabox.domain.user.dto.response.UserSignInResponseDto;
import com.minhwi.vocabox.domain.user.service.UserService;
import com.minhwi.vocabox.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signUp(@RequestBody UserSignUpRequestDto signUpDto) {
        userService.signUp(signUpDto);
        return BaseResponse.of(HttpStatus.CREATED, "회원가입에 성공했습니다.");
    }

    @PostMapping("/signin")
    public ResponseEntity<BaseResponse<UserSignInResponseDto>> signIn(@RequestBody UserSignInRequestDto signInDto) {
        UserSignInResponseDto responseDto = userService.signIn(signInDto);
        return BaseResponse.of(responseDto, HttpStatus.OK, "로그인에 성공했습니다.");
    }
}

