package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;

public interface TetrisService {
    public void broadcastTetrisMessage(TetrisMessageRequest message, String senderSessionId);
}
