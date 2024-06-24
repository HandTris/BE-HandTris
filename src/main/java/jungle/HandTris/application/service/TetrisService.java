package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import jungle.HandTris.presentation.dto.request.TetrisRoomOwnerRequest;
import org.springframework.messaging.MessageHeaders;

public interface TetrisService {
    void broadcastTetrisMessage(TetrisMessageRequest message);
    MessageHeaders createHeaders(String sessionId);
    void checkRoomOwnerAndReady(TetrisRoomOwnerRequest ownerRequest);
    void readyToGuest(TetrisRoomOwnerRequest ownerRequest);
    void startTetris(TetrisRoomOwnerRequest ownerRequest);
}