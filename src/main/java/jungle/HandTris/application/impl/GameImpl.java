package jungle.HandTris.application.impl;

import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.GameService;
import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.exception.GameNotFoundException;
import jungle.HandTris.domain.repo.GameRepository;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class GameImpl implements GameService {
    private final GameRepository gameRepository;

    public List<Game> getGameList() {
        return gameRepository.findAllByGameStatusNotPlaying();
    }

    public UUID createGame(GameDetailReq gameDetailReq) {
        Game createdGame = new Game(gameDetailReq);
        gameRepository.save(createdGame);
        return createdGame.getUuid();
    }

    public Game enterGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        game.enter();
        gameRepository.save(game);
        return game;
    }

    public Game exitGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        game.exit();
        if (game.getParticipantCount() == 0) {
            deleteGame(gameId);
            return game;
        }
        gameRepository.save(game);
        return game;
    }

    public void deleteGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
        gameRepository.delete(game);
    }
}
