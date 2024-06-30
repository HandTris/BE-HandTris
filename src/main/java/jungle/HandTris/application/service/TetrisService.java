package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.response.RoomOwnerRes;

public interface TetrisService {
    RoomOwnerRes checkRoomOwnerAndReady(String roomCode);
}