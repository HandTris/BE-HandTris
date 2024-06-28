package jungle.HandTris.webSocket;

import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.response.RoomOwnerRes;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // MockitoExtension 사용
class MockTetrisServiceTest {

    @InjectMocks
    private TetrisService tetrisService;

    @Mock
    private MemberConnectionService memberConnectionService;

    @Test
    void checkRoomOwnerAndReady_singleUser() {
        // given
        when(memberConnectionService.getRoomMemberCount()).thenReturn(1);

        // when
        RoomOwnerRes result = tetrisService.checkRoomOwnerAndReady();

        // then
        assertEquals(new RoomOwnerRes(true), result);
    }

    @Test
    void checkRoomOwnerAndReady_multipleUsers() {
        // given
        when(memberConnectionService.getRoomMemberCount()).thenReturn(2);

        // when
        RoomOwnerRes result = tetrisService.checkRoomOwnerAndReady();

        // then
        assertEquals(new RoomOwnerRes(false), result);
    }
}

