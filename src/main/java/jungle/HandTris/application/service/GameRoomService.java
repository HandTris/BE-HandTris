package jungle.HandTris.application.service;

import jungle.HandTris.domain.GameRoom;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;

import java.util.List;
import java.util.UUID;

public interface GameRoomService {
    List<GameRoom> getGameRoomList();

    UUID createGameRoom(GameRoomDetailReq gameRoomDetailReq);

    GameRoom enterGameRoom(long gameId);

    GameRoom exitGameRoom(long gameId);

}
