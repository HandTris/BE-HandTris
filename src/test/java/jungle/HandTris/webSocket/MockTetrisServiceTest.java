package jungle.HandTris.webSocket;

import jungle.HandTris.application.impl.TetrisServiceImpl;
import jungle.HandTris.application.service.GameRoomService;
import jungle.HandTris.presentation.dto.response.RoomOwnerRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // MockitoExtension 사용
class MockTetrisServiceTest {

    @InjectMocks
    private TetrisServiceImpl tetrisService;

    @Mock
    private GameRoomService gameRoomService;

    @Test
    void checkRoomOwnerAndReady_singleUser(String roomCode) {
        // given
        when(gameRoomService.getGameRoom(roomCode).getParticipantCount()).thenReturn(1);

        // when
        RoomOwnerRes result = tetrisService.checkRoomOwnerAndReady(roomCode);

        // then
        assertEquals(new RoomOwnerRes(true), result);
    }

    @Test
    void checkRoomOwnerAndReady_multipleUsers(String roomCode) {
        // given
        when(gameRoomService.getGameRoom(roomCode).getParticipantCount()).thenReturn(2);

        // when
        RoomOwnerRes result = tetrisService.checkRoomOwnerAndReady(roomCode);

        // then
        assertEquals(new RoomOwnerRes(false), result);
    }
}

