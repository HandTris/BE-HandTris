package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.application.service.TetrisService;
import jungle.HandTris.presentation.dto.response.RoomOwnerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TetrisServiceImpl implements TetrisService {
    private final MemberConnectionService memberConnectionService;

    public RoomOwnerRes checkRoomOwnerAndReady() {
        // 첫 번째 유저 방장 부여
//        String firstUser = memberConnectionService.getFirstUser();
        if (memberConnectionService.getFirstUser() != null) {
            return new RoomOwnerRes(true);
        }
        return new RoomOwnerRes(false);
    }

}