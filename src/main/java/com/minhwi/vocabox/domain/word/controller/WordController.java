package com.minhwi.vocabox.domain.word.controller;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDTO;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDTO;
import com.minhwi.vocabox.domain.word.entity.Word;
import com.minhwi.vocabox.domain.word.service.WordService;
import com.minhwi.vocabox.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.minhwi.vocabox.global.common.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> register(@RequestBody WordRequestDTO word) {
        wordService.register(word);
        return BaseResponse.of(HttpStatus.CREATED, "단어 등록에 성공하셨습니다.");
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<WordResponseDTO>>> findAll() {
        List<WordResponseDTO> words = wordService.findAll();
        return BaseResponse.of(words, HttpStatus.OK, "단어 목록 조회에 성공하셨습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> removeById(
            @PathVariable Long id
    ) {
        wordService.removeById(id);
        return BaseResponse.of(HttpStatus.OK, "단어 삭제에 성공하셨습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updateById(
            @PathVariable Long id,
            @RequestBody Word word
    ) {
        wordService.updateById(id, word);
        return BaseResponse.of(HttpStatus.OK, "단어 갱신에 성공하셨습니다.");
    }
}
