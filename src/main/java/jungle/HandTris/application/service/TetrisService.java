package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;

public interface TetrisService {
    void broadcastTetrisMessage(TetrisMessageRequest message);
}
