package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.GameStatus;

import java.util.UUID;

public record GameDetailRes(
        long id,
        GameCategory gameCategory,
        long participantCount,
        long participantLimit,
        UUID uuid,
        GameStatus gameStatus
) {
    public GameDetailRes(Game game) {
        this(game.getId(),
                game.getGameCategory(),
                game.getParticipantCount(),
                game.getParticipantLimit(),
                game.getUuid(),
                game.getGameStatus());
    }
}
