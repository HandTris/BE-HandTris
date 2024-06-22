package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TetrisImplement implements TetrisService {
    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastTetrisMessage(TetrisMessageRequest message) {
        messagingTemplate.convertAndSend("/topic/tetris", message);
    }
}