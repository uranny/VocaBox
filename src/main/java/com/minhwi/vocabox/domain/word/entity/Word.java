package com.minhwi.vocabox.domain.word.entity;

import com.minhwi.vocabox.domain.word.dto.request.WordRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    public static Word toEntity(WordRequestDTO dto) {
        Word word = new Word();
        word.setWord(dto.getWord().trim());
        word.setMeaning(dto.getMeaning().trim());
        return word;
    }
}
