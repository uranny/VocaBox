package com.minhwi.vocabox.domain.word.dto.response;

import com.minhwi.vocabox.domain.word.entity.Word;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponseDTO {
    private Long id;
    private String word;
    private String meaning;

    public static WordResponseDTO toResponseDTO(Word word) {
        return new WordResponseDTO(word.getId(), word.getWord(), word.getMeaning());
    }
}
