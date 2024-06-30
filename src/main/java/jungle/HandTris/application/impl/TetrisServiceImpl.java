package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.GameRoomService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.response.RoomOwnerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TetrisServiceImpl implements TetrisService {

    private final GameRoomService gameRoomService;

    public RoomOwnerRes checkRoomOwnerAndReady(String roomCode) {
        if (gameRoomService.getGameRoom(roomCode).getParticipantCount() == 1) {
            return new RoomOwnerRes(true);
        }
        return new RoomOwnerRes(false);
    }

}