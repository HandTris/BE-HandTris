package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import jungle.HandTris.presentation.dto.request.TetrisRoomOwnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
                        messagingTemplate.convertAndSendToUser(sessionId, "queue/tetris", message, createHeaders(sessionId))
                );
    }

    public void checkRoomOwnerAndReady(TetrisRoomOwnerRequest ownerRequest) {
        // 첫 번째 유저 방장 부여
        String firstUser = memberConnectionService.getFirstUser();
        ownerRequest.setOwner(firstUser.equals(ownerRequest.getSessionId()));

        messagingTemplate.convertAndSend("/topic/tetris", ownerRequest);
    }

    public void readyToGuest(TetrisRoomOwnerRequest ownerRequest) {
        if(ownerRequest.isReady()){
            messagingTemplate.convertAndSend("/topic/tetris", ownerRequest);
        }
    }

    public void startTetris(TetrisRoomOwnerRequest ownerRequest) {
        if(ownerRequest.isStart()){
            messagingTemplate.convertAndSend("/topic/tetris", ownerRequest);
        }
    }

    public MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}