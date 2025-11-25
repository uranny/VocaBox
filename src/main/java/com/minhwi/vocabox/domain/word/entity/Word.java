package com.minhwi.vocabox.domain.word.entity;

import com.minhwi.vocabox.domain.user.entity.User;
import com.minhwi.vocabox.domain.word.dto.request.WordRequestDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Word toEntity(WordRequestDto dto, User user) {
        Word word = new Word();
        word.setWord(dto.getWord().trim());
        word.setMeaning(dto.getMeaning().trim());
        word.setUser(user);
        return word;
    }
}
