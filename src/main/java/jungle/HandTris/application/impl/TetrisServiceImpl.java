package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TetrisServiceImpl implements TetrisService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberConnectionService memberConnectionService;

    public void broadcastTetrisMessage(TetrisMessageRequest message) {
        Set<String> connectedUsers = memberConnectionService.getAllUsers();

        connectedUsers.stream()
                .filter(sessionId -> !sessionId.equals(message.sender()))
                .forEach(sessionId ->
                        messagingTemplate.convertAndSendToUser(sessionId, "/queue/tetris", message, createHeaders(sessionId))
                );
    }

    public MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}