package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.GameStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GameDetailRes {

    private final long id;
    private final GameCategory gameCategory;
    private final long participantCount;
    private final long participantLimit;
    private final UUID uuid;
    private final GameStatus gameStatus;

    public GameDetailRes(Game game) {
        this.id = game.getId();
        this.gameCategory = game.getGameCategory();
        this.participantCount = game.getParticipantCount();
        this.participantLimit = game.getParticipantLimit();
        this.uuid = game.getUuid();
        this.gameStatus = game.getGameStatus();
    }
}
