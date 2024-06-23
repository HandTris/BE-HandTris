package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import org.springframework.messaging.MessageHeaders;

public interface TetrisService {
    void broadcastTetrisMessage(TetrisMessageRequest message);
    MessageHeaders createHeaders(String sessionId);
}