package com.minhwi.vocabox.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minhwi.vocabox.domain.user.dto.request.UserSignInRequestDto;
import com.minhwi.vocabox.domain.user.dto.request.UserSignUpRequestDto;
import com.minhwi.vocabox.domain.user.dto.response.UserSignInResponseDto;
import com.minhwi.vocabox.domain.user.service.UserService;
import com.minhwi.vocabox.global.config.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 API - 성공")
    void signUpApi_Success() throws Exception {
        // given
                UserSignUpRequestDto signUpDto = new UserSignUpRequestDto();
        signUpDto.setUsername("testuser");
        signUpDto.setPassword("password123");
        willDoNothing().given(userService).signUp(any(UserSignUpRequestDto.class));

        // when
        ResultActions result = mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("회원가입에 성공했습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 API - 성공")
    void signInApi_Success() throws Exception {
        // given
                UserSignInRequestDto signInDto = new UserSignInRequestDto();
        signInDto.setUsername("testuser");
        signInDto.setPassword("password123");
        UserSignInResponseDto responseDto = new UserSignInResponseDto("test.token");
        given(userService.signIn(any(UserSignInRequestDto.class))).willReturn(responseDto);

        // when
        ResultActions result = mockMvc.perform(post("/users/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInDto)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
                .andExpect(jsonPath("$.data.accessToken").value("test.token"))
                .andDo(print());
    }
}
