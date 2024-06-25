package jungle.HandTris.application.impl;

import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.GameRoomService;
import jungle.HandTris.domain.GameRoom;
import jungle.HandTris.domain.GameStatus;
import jungle.HandTris.domain.exception.GameRoomNotFoundException;
import jungle.HandTris.domain.exception.ParticipantLimitedException;
import jungle.HandTris.domain.exception.PlayingGameException;
import jungle.HandTris.domain.repo.GameRoomRepository;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {
    private final GameRoomRepository gameRoomRepository;

    public List<GameRoom> getGameRoomList() {
        return gameRoomRepository.findAllByGameStatusNotPlaying();
    }

    public UUID createGameRoom(GameRoomDetailReq gameRoomDetailReq) {
        GameRoom createdGameRoom = new GameRoom(gameRoomDetailReq);
        gameRoomRepository.save(createdGameRoom);
        return createdGameRoom.getUuid();
    }

    public GameRoom enterGameRoom(long gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow(GameRoomNotFoundException::new);

        if (gameRoom.getGameStatus() == GameStatus.PLAYING) {
            throw new PlayingGameException();
        }

        if (gameRoom.getParticipantCount() == gameRoom.getParticipantLimit()) {
            throw new ParticipantLimitedException();
        }

        gameRoom.enter();
        gameRoomRepository.save(gameRoom);
        
        return gameRoom;
    }

    public GameRoom exitGameRoom(long gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow(GameRoomNotFoundException::new);
        gameRoom.exit();
        if (gameRoom.getParticipantCount() == 0) {
            deleteGameRoom(gameId);
            return gameRoom;
        }
        gameRoomRepository.save(gameRoom);
        return gameRoom;
    }

    public void deleteGameRoom(long gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow(GameRoomNotFoundException::new);
        gameRoomRepository.delete(gameRoom);
    }
}
