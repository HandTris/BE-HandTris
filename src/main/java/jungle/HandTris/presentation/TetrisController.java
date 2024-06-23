package jungle.HandTris.presentation;

import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import jungle.HandTris.presentation.dto.request.TetrisRoomOwnerRequest;
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

    @MessageMapping("/owner/info")
    public void roomOwnerInfo(TetrisRoomOwnerRequest ownerRequest) {
        tetrisService.checkRoomOwnerAndReady(ownerRequest);
    }

    @MessageMapping("/tetris/ready")
    public void TetrisReady(TetrisRoomOwnerRequest ownerRequest) {
        tetrisService.readyToGuest(ownerRequest);
    }

    @MessageMapping("/tetris/start")
    public void TetrisStart(TetrisRoomOwnerRequest ownerRequest) {
        tetrisService.startTetris(ownerRequest);
    }
}