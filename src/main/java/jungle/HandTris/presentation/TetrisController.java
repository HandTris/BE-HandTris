package jungle.HandTris.presentation;

import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import jungle.HandTris.application.service.TetrisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TetrisController {

    private final TetrisService tetrisService;

    @MessageMapping("/tetris")
    public void handleTetrisMessage(TetrisMessageRequest message) {
        tetrisService.broadcastTetrisMessage(message);
    }
}
