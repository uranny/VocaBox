package com.minhwi.vocabox.domain.word.service;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDto;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDto;

import java.util.List;

public interface WordService {
    WordResponseDto register(String username, WordRequestDto word);
    List<WordResponseDto> findAll(String username);
    void removeById(String username, Long id);
    WordResponseDto updateById(String username, Long id, WordRequestDto wordDto);
    List<WordResponseDto> findByWord(String username, String word);
}
