package com.minhwi.vocabox.domain.word.service;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDTO;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDTO;
import com.minhwi.vocabox.domain.word.entity.Word;
import com.minhwi.vocabox.domain.word.repository.WordRepository;
import com.minhwi.vocabox.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
    @Override
    public void register(WordRequestDTO word) {
        if(!StringUtils.hasText(word.getWord()) || !StringUtils.hasText(word.getMeaning())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "단어와 뜻은 필수입니다.");
        }
        wordRepository.save(Word.toEntity(word));
    }

    @Override
    public List<WordResponseDTO> findAll() {
        List<Word> results = wordRepository.findAll();
        if(results.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "등록된 단어가 없습니다.");
        }
        return results.stream()
                .map(WordResponseDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeById(Long id) {
        if(!wordRepository.existsById(id)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제할 단어가 존재하지 않습니다.");
        }
        wordRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, Word word) {
        Word existingWord = wordRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "수정할 단어가 존재하지 않습니다."));

        if(StringUtils.hasText(word.getWord())) existingWord.setWord(word.getWord());
        if(StringUtils.hasText(word.getMeaning())) existingWord.setMeaning(word.getMeaning());

        wordRepository.save(existingWord);
    }
}
