package jungle.HandTris.presentation;

import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.request.TetrisMessageRequest;
import jungle.HandTris.presentation.dto.request.RoomStateReq;
import jungle.HandTris.presentation.dto.response.RoomOwnerRes;
import jungle.HandTris.presentation.dto.response.RoomStateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TetrisController {
    private final SimpMessagingTemplate messagingTemplate;

    private final MemberConnectionService memberConnectionService;

    private final TetrisService tetrisService;

    @MessageMapping("/tetris")
    public void handleTetrisMessage(TetrisMessageRequest message) {
        Set<String> connectedUsers = memberConnectionService.getAllUsers();
        connectedUsers.stream()
                .filter(sessionId -> !sessionId.equals(message.sender()))
                .forEach(sessionId ->
                        messagingTemplate.convertAndSendToUser(sessionId, "queue/tetris", message, createHeaders(sessionId))
                );
    }

    @MessageMapping("/owner/info")
    public void roomOwnerInfo() {
        RoomOwnerRes roomOwnerRes = tetrisService.checkRoomOwnerAndReady();

        messagingTemplate.convertAndSend("/topic/owner", roomOwnerRes);
    }

    @MessageMapping("/tetris/ready")
    public void TetrisReady(RoomStateReq req) {
        if(req.isReady()){
            RoomStateRes res = new RoomStateRes(true,false);
            messagingTemplate.convertAndSend("/topic/state", res);
        }
    }

    @MessageMapping("/tetris/start")
    public void TetrisStart(RoomStateReq req) {
        if (req.isStart()) {
            RoomStateRes res = new RoomStateRes(true,true);
            messagingTemplate.convertAndSend("/topic/state", res);
        }
    }

    @GetMapping("/user/clear")
    public void clearUser(){
        if(memberConnectionService.getRoomMemberCount() != 0){
            memberConnectionService.clearUser();
        }
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}