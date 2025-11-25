package com.minhwi.vocabox.domain.user.repository;

import com.minhwi.vocabox.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByUsername - 성공")
    @Sql("/data.sql")
    void findByUsername_Success() {
        // when
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("findByUsername - 실패 (사용자 없음)")
    @Sql("/data.sql")
    void findByUsername_Fail() {
        // when
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // then
        assertThat(foundUser).isEmpty();
    }
}
