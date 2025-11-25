package com.minhwi.vocabox.domain.word.repository;

import com.minhwi.vocabox.domain.user.entity.User;
import com.minhwi.vocabox.domain.word.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByUser(User user);
    List<Word> findByUserAndWordContainingIgnoreCase(User user, String word);
}
