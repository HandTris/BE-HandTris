package jungle.HandTris.presentation;

import jungle.HandTris.application.service.GameMemberService;
import jungle.HandTris.presentation.dto.request.RoomStateReq;
import jungle.HandTris.presentation.dto.request.TetrisMessageReq;
import jungle.HandTris.presentation.dto.response.RoomStateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequiredArgsConstructor
public class TetrisController {
    private final SimpMessagingTemplate messagingTemplate;

    private final GameMemberService gameMemberService;

//    private final TetrisService tetrisService;

    @MessageMapping("/{roomCode}/tetris")
    public void handleTetrisMessage(@DestinationVariable String roomCode, TetrisMessageReq message) {
        Set<String> connectedUsers = gameMemberService.getUsersByRoomCode(roomCode);
        connectedUsers.stream()
                .filter(MemberId -> !MemberId.equals(message.sender())) //TODO User Naming -> Member
                .forEach(MemberId ->
                        messagingTemplate.convertAndSendToUser(MemberId, "queue/tetris", message)
                );
    }

//    @MessageMapping("/{roomCode}/owner/info")
//    public void roomOwnerInfo(@DestinationVariable String roomCode) {
//        RoomOwnerRes roomOwnerRes = tetrisService.checkRoomOwnerAndReady();
//        messagingTemplate.convertAndSend("/topic/owner/" + roomCode, roomOwnerRes); //TOPIC은 Prefix이기 때문에 roomCode가 뒤에 가도록 변경해주세요
//    }

    @MessageMapping("/{roomCode}/tetris/ready")
    public void TetrisReady(@DestinationVariable String roomCode, RoomStateReq req) {
        if (req.isReady()) {
            RoomStateRes res = new RoomStateRes(true, false);
            messagingTemplate.convertAndSend("/topic/state/" + roomCode, res);
        }
    }

    @MessageMapping("/{roomCode}/tetris/start")
    public void TetrisStart(@DestinationVariable String roomCode, RoomStateReq req) {
        if (req.isStart()) {
            RoomStateRes res = new RoomStateRes(true, true);
            messagingTemplate.convertAndSend("/topic/state/" + roomCode, res);
        }
    }

//    @GetMapping("/user/clear")
//    public void clearUser(){
//        if(memberConnectionService.getRoomMemberCount() != 0){
//            memberConnectionService.clearUser();
//        }
//    }

//    private MessageHeaders createHeaders(String sessionId) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//        return headerAccessor.getMessageHeaders();
//    }
}