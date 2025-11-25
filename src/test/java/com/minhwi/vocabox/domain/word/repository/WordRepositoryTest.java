package com.minhwi.vocabox.domain.word.repository;

import com.minhwi.vocabox.domain.user.entity.User;
import com.minhwi.vocabox.domain.user.repository.UserRepository;
import com.minhwi.vocabox.domain.word.entity.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByUser - 특정 사용자의 단어 목록 조회")
    @Sql("/data.sql")
    void findByUser() {
        // given
        User testUser = userRepository.findByUsername("testuser").get();

        // when
        List<Word> words = wordRepository.findByUser(testUser);

        // then
        assertThat(words).hasSize(3);
    }

    @Test
    @DisplayName("findByUserAndWordContainingIgnoreCase - 특정 사용자의 단어 검색")
    @Sql("/data.sql")
    void findByUserAndWordContainingIgnoreCase() {
        // given
        User testUser = userRepository.findByUsername("testuser").get();

        // when
        List<Word> results = wordRepository.findByUserAndWordContainingIgnoreCase(testUser, "comp");

        // then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getWord()).isEqualTo("computer");
    }

    @Test
    @DisplayName("findByUserAndWordContainingIgnoreCase - 검색 결과 없음")
    @Sql("/data.sql")
    void findByUserAndWordContainingIgnoreCase_NoResult() {
        // given
        User testUser = userRepository.findByUsername("testuser").get();

        // when
        List<Word> results = wordRepository.findByUserAndWordContainingIgnoreCase(testUser, "xyz");

        // then
        assertThat(results).isEmpty();
    }

}
