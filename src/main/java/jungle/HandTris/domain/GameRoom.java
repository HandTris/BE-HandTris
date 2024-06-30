package jungle.HandTris.domain;

import jakarta.persistence.*;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameCategory gameCategory;

    private int participantCount;

    private int participantLimit;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;

    public GameRoom(GameRoomDetailReq gameRoomDetailReq) {
        this.gameCategory = GameCategory.valueOf(GameCategory.class, gameRoomDetailReq.gameCategory());
        this.participantLimit = gameRoomDetailReq.participantLimit();
        this.uuid = UUID.randomUUID();
        this.gameStatus = GameStatus.NON_PLAYING;
        this.participantCount = 1;
    }

    public void enter() {
        this.participantCount++;
    }

    public void exit() {
        this.participantCount--;
    }

}
