package jungle.HandTris.domain;

import jakarta.persistence.*;
import jungle.HandTris.domain.exception.ParticipantLimitedException;
import jungle.HandTris.domain.exception.PlayingGameException;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameCategory gameCategory;

    private long participantCount;

    private long participantLimit;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;

    public Game(GameDetailReq gameDetailReq) {
        this.gameCategory = GameCategory.valueOf(GameCategory.class, gameDetailReq.gameCategory());
        this.participantLimit = gameDetailReq.participantLimit();
        this.uuid = UUID.randomUUID();
        this.gameStatus = GameStatus.NON_PLAYING;
        this.participantCount = 1;
    }

    public void enter() {
        if (gameStatus == GameStatus.PLAYING) {
            throw new PlayingGameException();

        } else if (participantLimit == participantCount) {
            throw new ParticipantLimitedException();
        } else {
            this.participantCount++;
            System.out.println("your in the game");
        }
    }

    public void exit() {
        if (gameStatus == GameStatus.PLAYING) {
            throw new PlayingGameException();
        } else if (participantLimit == 1) {
            System.out.println("Game will be deleted");
        } else {
            this.participantCount--;
            System.out.println("successfully exited");
        }
    }

}
