DROP PROCEDURE IF EXISTS insert_dummy_data;

CREATE PROCEDURE insert_dummy_data(IN num_members INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE username VARCHAR(255);
    DECLARE `password` VARCHAR(255); -- 변수 이름을 백틱으로 묶음
    DECLARE nickname VARCHAR(255);

    WHILE i <= num_members
        DO
            SET username = CONCAT('user', i);
            SET `password` = 'password'; -- 모든 사용자의 비밀번호를 동일하게 설정 (실제 환경에서는 변경 필요)
            SET nickname = CONCAT('nickname', i);

            INSERT INTO member (username, `password`, nickname) VALUES (username, `password`, nickname);

            SET i = i + 1;
        END WHILE;

    -- GameRoom 데이터 생성 (1000개)
    SET i = 1;
    WHILE i <= 1000
        DO
            INSERT INTO game_room (creator, title, participant_count, participant_limit, room_code, game_status)
            VALUES ((SELECT nickname FROM member ORDER BY RAND() LIMIT 1),
                    CONCAT('Game Room ', i),
                    FLOOR(RAND() * 2) + 1, -- participant_count (1 또는 2)
                    2,
                    UUID_TO_BIN(UUID()),
                    'NON_PLAYING');

            SET i = i + 1;
        END WHILE;

    -- MemberRecord 데이터 생성 (Member 수만큼)
    INSERT INTO member_record (member_id, win, lose, rate)
    SELECT id, FLOOR(RAND() * 100), FLOOR(RAND() * 100), ROUND(RAND() * 100, 2)
    FROM member;
END;
CALL insert_dummy_data(1000);
