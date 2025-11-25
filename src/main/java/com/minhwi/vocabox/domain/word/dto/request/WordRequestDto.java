package com.minhwi.vocabox.domain.word.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordRequestDto {
    private String word;
    private String meaning;
}
