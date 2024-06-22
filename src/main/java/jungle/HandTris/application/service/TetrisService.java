package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import org.springframework.messaging.MessageHeaders;

public interface TetrisService {
    public void broadcastTetrisMessage(TetrisMessageRequest message);
    public MessageHeaders createHeaders(String sessionId);
}