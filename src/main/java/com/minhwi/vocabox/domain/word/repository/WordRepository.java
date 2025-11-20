package com.minhwi.vocabox.domain.word.repository;

import com.minhwi.vocabox.domain.word.dto.request.WordOnlyRequestDTO;
import com.minhwi.vocabox.domain.word.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByWord(String word);
}
