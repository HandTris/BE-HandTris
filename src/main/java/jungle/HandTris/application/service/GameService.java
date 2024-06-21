package jungle.HandTris.application.service;

import jakarta.transaction.Transactional;
import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.exception.GameNotFoundException;
import jungle.HandTris.domain.repo.GameRepository;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public List<Game> getGameList() {
        return gameRepository.findAllByGameStatusNotPlaying();
    }

    public Game createGame(GameDetailReq gameDetailReq) {
        /*
        입력 데이터
            - 게임 카테고리
            - 총 인원
        default값
            - 참가인원 : 1
            - uuid : 자동 생성
            - gameStatus : NON_PLAYING
         */
        Game createdGame = new Game(gameDetailReq);
        return gameRepository.save(createdGame);
    }

    public Game enterGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null)
            throw new GameNotFoundException();

        game.enter();
        gameRepository.save(game);
        return game;
    }

    public Game exitGame(long gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null)
            throw new GameNotFoundException();

        game.exit();
        if (game.getParticipantCount() == 0) {
            deleteGame(gameId);
            return game;
        }
        gameRepository.save(game);
        return game;
    }

    public void deleteGame(long gameId) {

        if (!gameRepository.existsById(gameId))
            throw new GameNotFoundException();

        gameRepository.deleteById(gameId);
    }
}
