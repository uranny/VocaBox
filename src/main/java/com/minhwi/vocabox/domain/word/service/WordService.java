package com.minhwi.vocabox.domain.word.service;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDTO;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDTO;
import com.minhwi.vocabox.domain.word.entity.Word;

import java.util.List;

public interface WordService {
    void register(WordRequestDTO word);
    List<WordResponseDTO> findAll();
    void removeById(Long id);
    void updateById(Long id, Word word);
}
