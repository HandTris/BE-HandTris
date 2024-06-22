package jungle.HandTris.presentation;

import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TetrisController {

    private final TetrisService tetrisService;

    @MessageMapping("/tetris")
    @SendTo("/topic/tetris")
    public TetrisMessageRequest handleTetrisMessage(TetrisMessageRequest message) {
        tetrisService.broadcastTetrisMessage(message);
        return message;
    }
}
