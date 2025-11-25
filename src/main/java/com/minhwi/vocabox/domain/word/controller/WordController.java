package com.minhwi.vocabox.domain.word.controller;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDto;
import com.minhwi.vocabox.domain.word.dto.response.WordResponseDto;
import com.minhwi.vocabox.domain.word.service.WordService;
import com.minhwi.vocabox.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @PostMapping
    public ResponseEntity<BaseResponse<WordResponseDto>> register(Principal principal, @RequestBody WordRequestDto word) {
        WordResponseDto responseDto = wordService.register(principal.getName(), word);
        return BaseResponse.of(responseDto, HttpStatus.CREATED, "단어 등록에 성공하셨습니다.");
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<WordResponseDto>>> findAll(Principal principal) {
        List<WordResponseDto> words = wordService.findAll(principal.getName());
        return BaseResponse.of(words, HttpStatus.OK, "단어 목록 조회에 성공하셨습니다.");
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<WordResponseDto>>> findByWord(Principal principal, @RequestParam String word) {
        List<WordResponseDto> words = wordService.findByWord(principal.getName(), word);
        return BaseResponse.of(words, HttpStatus.OK, "단어 목록 조회에 성공하셨습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> removeById(Principal principal, @PathVariable Long id) {
        wordService.removeById(principal.getName(), id);
        return BaseResponse.of(HttpStatus.OK, "단어 삭제에 성공하셨습니다.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<WordResponseDto>> updateById(Principal principal, @PathVariable Long id, @RequestBody WordRequestDto wordDto) {
        WordResponseDto responseDto = wordService.updateById(principal.getName(), id, wordDto);
        return BaseResponse.of(responseDto, HttpStatus.OK, "단어 갱신에 성공하셨습니다.");
    }
}
