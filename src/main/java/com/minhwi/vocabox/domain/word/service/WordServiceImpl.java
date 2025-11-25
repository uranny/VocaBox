package com.minhwi.vocabox.domain.word.service;

import com.minhwi.vocabox.domain.user.entity.User;
import com.minhwi.vocabox.domain.user.repository.UserRepository;
import com.minhwi.vocabox.domain.word.dto.request.WordRequestDto;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDto;
import com.minhwi.vocabox.domain.word.entity.Word;
import com.minhwi.vocabox.domain.word.repository.WordRepository;
import com.minhwi.vocabox.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final UserRepository userRepository;

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public WordResponseDto register(String username, WordRequestDto wordDto) {
        if (!StringUtils.hasText(wordDto.getWord().trim()) || !StringUtils.hasText(wordDto.getMeaning().trim())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "단어와 뜻은 필수입니다.");
        }
        User user = getUserByUsername(username);
        Word word = Word.toEntity(wordDto, user);
        Word savedWord = wordRepository.save(word);
        return WordResponseDto.toResponseDTO(savedWord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordResponseDto> findAll(String username) {
        User user = getUserByUsername(username);
        List<Word> results = wordRepository.findByUser(user);
        return results.stream()
                .map(WordResponseDto::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeById(String username, Long id) {
        User user = getUserByUsername(username);
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "삭제할 단어가 존재하지 않습니다."));

        if (!Objects.equals(word.getUser().getId(), user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "단어에 대한 권한이 없습니다.");
        }

        wordRepository.deleteById(id);
    }

    @Override
    @Transactional
    public WordResponseDto updateById(String username, Long id, WordRequestDto wordDto) {
        User user = getUserByUsername(username);
        Word existingWord = wordRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "수정할 단어가 존재하지 않습니다."));

        if (!Objects.equals(existingWord.getUser().getId(), user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "단어에 대한 권한이 없습니다.");
        }

        if (wordDto.getWord() != null && StringUtils.hasText(wordDto.getWord().trim())) {
            existingWord.setWord(wordDto.getWord().trim());
        }

        if (wordDto.getMeaning() != null && StringUtils.hasText(wordDto.getMeaning().trim())) {
            existingWord.setMeaning(wordDto.getMeaning().trim());
        }

        Word updatedWord = wordRepository.save(existingWord);
        return WordResponseDto.toResponseDTO(updatedWord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordResponseDto> findByWord(String username, String word) {
        if (!StringUtils.hasText(word)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "단어는 필수입니다.");
        }
        User user = getUserByUsername(username);
        List<Word> results = wordRepository.findByUserAndWordContainingIgnoreCase(user, word.trim());
        return results.stream()
                .map(WordResponseDto::toResponseDTO)
                .collect(Collectors.toList());
    }
}
