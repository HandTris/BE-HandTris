package jungle.HandTris.presentation;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.GameRoomService;
import jungle.HandTris.domain.GameRoom;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;
import jungle.HandTris.presentation.dto.response.GameRoomDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameRoomController {
    private final GameRoomService gameRoomService;


    @GetMapping
    public ResponseEnvelope<List<GameRoomDetailRes>> getGameRoomList() {
        List<GameRoomDetailRes> gameRoomDetailResList = gameRoomService.getGameRoomList().stream().map(GameRoomDetailRes::new).toList();
        ResponseEnvelope<List<GameRoomDetailRes>> result = ResponseEnvelope.of(gameRoomDetailResList);
        return result;
    }

    @PostMapping
    public ResponseEnvelope<UUID> createGameRoom(@Valid @RequestBody GameRoomDetailReq gameRoomDetailReq) {
        UUID gameUuid = gameRoomService.createGameRoom(gameRoomDetailReq);
        ResponseEnvelope<UUID> result = ResponseEnvelope.of(gameUuid);
        return result;
    }

    @PostMapping("/{roomNumber}/enter")
    public ResponseEnvelope<GameRoomDetailRes> enterGameRoom(@PathVariable("roomNumber") String roomNumber) {
        GameRoom gameRoom = gameRoomService.enterGameRoom(roomNumber);
        GameRoomDetailRes gameRoomDetailRes = new GameRoomDetailRes(gameRoom);
        ResponseEnvelope<GameRoomDetailRes> result = ResponseEnvelope.of(gameRoomDetailRes);
        return result;
    }

    @PostMapping("/{roomNumber}/exit")
    public ResponseEnvelope<String> exitGameRoom(@PathVariable("roomNumber") String roomNumber) {
        GameRoom gameRoom = gameRoomService.exitGameRoom(roomNumber);
        ResponseEnvelope<String> result = ResponseEnvelope.of(roomNumber);
        return result;
    }

}
