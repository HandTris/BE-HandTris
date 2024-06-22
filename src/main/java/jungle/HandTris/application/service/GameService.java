package jungle.HandTris.application.service;

import jungle.HandTris.domain.Game;
import jungle.HandTris.presentation.dto.request.GameDetailReq;

import java.util.List;
import java.util.UUID;

public interface GameService {
    List<Game> getGameList();

    UUID createGame(GameDetailReq gameDetailReq);

    Game enterGame(long gameId);

    Game exitGame(long gameId);

    void deleteGame(long gameId);
}
