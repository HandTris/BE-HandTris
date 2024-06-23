package jungle.HandTris.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TetrisRoomOwnerRequest {
    private boolean isOwner;
    private boolean isReady;
    private boolean isStart;
    private String sessionId;
}