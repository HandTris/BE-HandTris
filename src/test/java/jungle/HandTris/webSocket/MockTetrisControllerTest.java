package jungle.HandTris.webSocket;

//import jungle.HandTris.application.service.TetrisService;

import jungle.HandTris.presentation.TetrisController;
import jungle.HandTris.presentation.dto.request.RoomStateReq;
import jungle.HandTris.presentation.dto.response.RoomStateRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // MockitoExtension 사용
class MockTetrisControllerTest {

    @InjectMocks
    private TetrisController tetrisController;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

//    @Mock
//    private TetrisService tetrisService;

//    @Test
//    @DisplayName("방장 권한 부여 Test")
//    void roomOwnerInfo(String roomCode) {
//        // given
//        RoomOwnerRes roomOwnerRes = new RoomOwnerRes(true);
//        // checkRoomOwnerAndReady 함수가 실행되는것이 아닌, 위의 true로 정해놓은 객체로 반환
//        when(tetrisService.checkRoomOwnerAndReady()).thenReturn(roomOwnerRes);
//
//        // when
//        tetrisController.roomOwnerInfo(roomCode);
//
//        // then
//        verify(messagingTemplate).convertAndSend("/topic/owner", roomOwnerRes);
//    }

    @Test
    @DisplayName("테트리스 준비 Test")
    void TetrisReady(String roomCode) {
        // given
        RoomStateReq req = new RoomStateReq(true, false);

        // when
        tetrisController.TetrisReady(roomCode, req);

        // then
        RoomStateRes expectedResponse = new RoomStateRes(true, false);
        verify(messagingTemplate).convertAndSend("/topic/state", expectedResponse);
    }

    @Test
    @DisplayName("테트리스 시작 Test")
    void TetrisStart(String roomCode) {
        // given
        RoomStateReq req = new RoomStateReq(true, true);

        // when
        tetrisController.TetrisStart(roomCode, req);

        // then
        RoomStateRes expectedResponse = new RoomStateRes(true, true);
        verify(messagingTemplate).convertAndSend("/topic/state", expectedResponse);
    }
}

