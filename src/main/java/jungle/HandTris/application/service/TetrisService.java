package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TetrisService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserConnectionService userConnectionService;

    public void broadcastTetrisMessage(TetrisMessageRequest message) {
        Set<String> connectedUsers = userConnectionService.getAllUsers();

        for (String sessionId : connectedUsers) {
            if (!sessionId.equals(message.sender())) {
                messagingTemplate.convertAndSendToUser(sessionId, "queue/tetris", message, createHeaders(sessionId));
            }
        }
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}