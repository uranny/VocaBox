package com.minhwi.vocabox.domain.word.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordRequestDTO {
    private String word;
    private String meaning;
}
