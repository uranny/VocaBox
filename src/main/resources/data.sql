-- 사용자 데이터 (비밀번호는 'password'를 BCrypt로 인코딩한 값)
INSERT INTO users (id, username, password) VALUES (1, 'testuser', '$2a$10$y.d.l5.OAn4.dGYs2s9qN.6/2Qz.mMbJ9G4ZDBv2c.CgYd5O.tqSm');
INSERT INTO users (id, username, password) VALUES (2, 'otheruser', '$2a$10$y.d.l5.OAn4.dGYs2s9qN.6/2Qz.mMbJ9G4ZDBv2c.CgYd5O.tqSm');

-- testuser의 단어 데이터
INSERT INTO tb_words (word, meaning, user_id) VALUES ('apple', '사과', 1);
INSERT INTO tb_words (word, meaning, user_id) VALUES ('banana', '바나나', 1);
INSERT INTO tb_words (word, meaning, user_id) VALUES ('computer', '컴퓨터', 1);

-- otheruser의 단어 데이터
INSERT INTO tb_words (word, meaning, user_id) VALUES ('book', '책', 2);
INSERT INTO tb_words (word, meaning, user_id) VALUES ('desk', '책상', 2);
