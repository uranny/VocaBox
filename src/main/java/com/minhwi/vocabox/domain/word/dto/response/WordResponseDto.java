package com.minhwi.vocabox.domain.word.dto.response;

import com.minhwi.vocabox.domain.word.entity.Word;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponseDto {
    private Long id;
    private String word;
    private String meaning;

    public static WordResponseDto toResponseDTO(Word word) {
        return new WordResponseDto(word.getId(), word.getWord().trim(), word.getMeaning().trim());
    }
}
