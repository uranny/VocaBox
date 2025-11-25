package com.minhwi.vocabox.domain.user.service;

import com.minhwi.vocabox.domain.user.dto.request.UserSignInRequestDto;
import com.minhwi.vocabox.domain.user.dto.request.UserSignUpRequestDto;
import com.minhwi.vocabox.domain.user.dto.response.UserSignInResponseDto;
import com.minhwi.vocabox.domain.user.entity.User;
import com.minhwi.vocabox.domain.user.repository.UserRepository;
import com.minhwi.vocabox.global.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입 - 성공")
    void signUp_Success() {
        // given
        UserSignUpRequestDto signUpDto = new UserSignUpRequestDto();
        signUpDto.setUsername("newUser");
        signUpDto.setPassword("password");
        when(userRepository.findByUsername(signUpDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // when
        userService.signUp(signUpDto);

        // then
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 - 실패 (사용자 이름 중복)")
    void signUp_Fail_UserExists() {
        // given
        UserSignUpRequestDto signUpDto = new UserSignUpRequestDto();
        signUpDto.setUsername("existingUser");
        signUpDto.setPassword("password");
        when(userRepository.findByUsername(signUpDto.getUsername())).thenReturn(Optional.of(User.builder().build()));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signUp(signUpDto));
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 사용자 이름입니다.");
    }

    @Test
    @DisplayName("로그인 - 성공")
    void signIn_Success() {
        // given
        UserSignInRequestDto signInDto = new UserSignInRequestDto();
        signInDto.setUsername("testuser");
        signInDto.setPassword("password");
        User user = User.builder().username("testuser").password("encodedPassword").build();
        when(userRepository.findByUsername(signInDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(signInDto.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtProvider.createToken(user.getUsername())).thenReturn("test.token");

        // when
        UserSignInResponseDto responseDto = userService.signIn(signInDto);

        // then
        assertThat(responseDto.getAccessToken()).isEqualTo("test.token");
    }

    @Test
    @DisplayName("로그인 - 실패 (사용자 없음)")
    void signIn_Fail_UserNotFound() {
        // given
        UserSignInRequestDto signInDto = new UserSignInRequestDto();
        signInDto.setUsername("nonexistentuser");
        signInDto.setPassword("password");
        when(userRepository.findByUsername(signInDto.getUsername())).thenReturn(Optional.empty());

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signIn(signInDto));
        assertThat(exception.getMessage()).isEqualTo("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("로그인 - 실패 (비밀번호 불일치)")
    void signIn_Fail_PasswordMismatch() {
        // given
        UserSignInRequestDto signInDto = new UserSignInRequestDto();
        signInDto.setUsername("testuser");
        signInDto.setPassword("wrongpassword");
        User user = User.builder().username("testuser").password("encodedPassword").build();
        when(userRepository.findByUsername(signInDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(signInDto.getPassword(), user.getPassword())).thenReturn(false);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signIn(signInDto));
        assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}
