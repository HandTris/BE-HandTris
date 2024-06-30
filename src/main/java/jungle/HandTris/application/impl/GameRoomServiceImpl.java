package jungle.HandTris.application.impl;

import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.GameMemberService;
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
    // 캐시 사용을 위한 주입
    private final GameMemberService gameMemberService;

    public List<GameRoom> getGameRoomList() {
        return gameRoomRepository.findAllByGameStatusNotPlaying();
    }

    public UUID createGameRoom(GameRoomDetailReq gameRoomDetailReq) {
        GameRoom createdGameRoom = new GameRoom(gameRoomDetailReq);
        gameRoomRepository.save(createdGameRoom);

        // Redis에 방Id와 유저Id 매핑 후 저장
        gameMemberService.addMemberToRoom(createdGameRoom.getUuid().toString(), "JWT에서 얻은 유저ID");

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
        gameRoomRepository.save(gameRoom); // TODO 강호형쪽에서 삭제예정

        // Redis에 방Id와 유저Id 매핑 후 저장
        gameMemberService.addMemberToRoom(gameRoom.getUuid().toString(), "JWT에서 얻은 유저ID");

        return gameRoom;
    }

    public GameRoom exitGameRoom(long gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow(GameRoomNotFoundException::new);

        if (gameRoom.getGameStatus() == GameStatus.PLAYING) {
            throw new PlayingGameException();
        }
        gameRoom.exit();

        // Redis에서 방Id와 매핑된 유저Id 제거
        gameMemberService.removeMemberFromRoom(gameRoom.getUuid().toString(), "해당 유저Id");

        if (gameRoom.getParticipantCount() == 0) {
            deleteGameRoom(gameId);
            return gameRoom;
        }
        gameRoomRepository.save(gameRoom); // TODO 강호형쪽에서 삭제예정
        return gameRoom;
    }

    public void deleteGameRoom(long gameId) { // TODO 강호형쪽에서 삭제예정
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow(GameRoomNotFoundException::new);
        gameRoomRepository.delete(gameRoom);

        // Redis에서 방 삭제
        gameMemberService.deleteRoom(gameRoom.getUuid().toString());
    }
}
