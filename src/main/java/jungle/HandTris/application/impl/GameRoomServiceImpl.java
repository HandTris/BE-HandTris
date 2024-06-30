package jungle.HandTris.application.impl;

import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.GameRoomService;
import jungle.HandTris.domain.GameMember;
import jungle.HandTris.domain.GameRoom;
import jungle.HandTris.domain.GameStatus;
import jungle.HandTris.domain.exception.GameRoomNotFoundException;
import jungle.HandTris.domain.exception.ParticipantLimitedException;
import jungle.HandTris.domain.exception.PlayingGameException;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

    private final RedisTemplate<String, GameRoom> redisTemplate;
    private final RedisTemplate<String, GameMember> memberRedisTemplate;
    private static final String GAME_ROOM_KEY_PREFIX = "gameMember:";

    public List<GameRoom> getGameRoomList() {
        return redisTemplate.keys(GAME_ROOM_KEY_PREFIX + "*").stream()
                .map(key -> redisTemplate.opsForValue().get(key))
                .filter(gameRoom -> gameRoom != null && gameRoom.getGameStatus() != GameStatus.PLAYING)
                .collect(Collectors.toList());
    }

    public GameRoom getGameRoom(String roomCode) {
        String gameRoomKey = GAME_ROOM_KEY_PREFIX + roomCode;
        GameRoom gameRoom = redisTemplate.opsForValue().get(gameRoomKey);

        if (gameRoom == null) {
            throw new GameRoomNotFoundException();
        }

        return gameRoom;
    }

    public GameMember getGameMember(String roomCode) {
        String gameMemberKey = GAME_ROOM_KEY_PREFIX + roomCode;
        return memberRedisTemplate.opsForValue().get(gameMemberKey);
    }

    public UUID createGameRoom(GameRoomDetailReq gameRoomDetailReq) {
        GameRoom createdGameRoom = new GameRoom(gameRoomDetailReq);
        String gameRoomKey = GAME_ROOM_KEY_PREFIX + createdGameRoom.getUuid().toString();

        redisTemplate.opsForValue().set(gameRoomKey, createdGameRoom);

        return createdGameRoom.getUuid();
    }

    public GameRoom enterGameRoom(long gameId) {
        String gameRoomKey = GAME_ROOM_KEY_PREFIX + gameId;
        GameRoom gameRoom = redisTemplate.opsForValue().get(gameRoomKey);

        if (gameRoom == null) {
            throw new GameRoomNotFoundException();
        }

        if (gameRoom.getGameStatus() == GameStatus.PLAYING) {
            throw new PlayingGameException();
        }

        if (gameRoom.getParticipantCount() == gameRoom.getParticipantLimit()) {
            throw new ParticipantLimitedException();
        }

        gameRoom.enter();
        redisTemplate.opsForValue().set(gameRoomKey, gameRoom);

        return gameRoom;
    }

    public GameRoom exitGameRoom(long gameId) {
        String gameRoomKey = GAME_ROOM_KEY_PREFIX + gameId;
        GameRoom gameRoom = redisTemplate.opsForValue().get(gameRoomKey);

        if (gameRoom == null) {
            throw new GameRoomNotFoundException();
        }

        if (gameRoom.getGameStatus() == GameStatus.PLAYING) {
            throw new PlayingGameException();
        }
        gameRoom.exit();

        if (gameRoom.getParticipantCount() == 0) {
            deleteGameRoom(gameId);
            return gameRoom;
        }
        redisTemplate.opsForValue().set(gameRoomKey, gameRoom);
        return gameRoom;
    }

    public void deleteGameRoom(long gameId) {
        String gameRoomKey = GAME_ROOM_KEY_PREFIX + gameId;
        GameRoom gameRoom = redisTemplate.opsForValue().get(gameRoomKey);

        if (gameRoom == null) {
            throw new GameRoomNotFoundException();
        }
        redisTemplate.delete(gameRoomKey);
    }
}
