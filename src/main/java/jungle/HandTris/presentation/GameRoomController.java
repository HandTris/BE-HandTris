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
        ResponseEnvelope<List<GameRoomDetailRes>> result = new ResponseEnvelope<>("200", gameRoomDetailResList, "성공입니다.");
        return result;
    }

    @PostMapping
    public ResponseEnvelope<UUID> createGameRoom(@Valid @RequestBody GameRoomDetailReq gameRoomDetailReq) {
        UUID gameUuid = gameRoomService.createGameRoom(gameRoomDetailReq);
        ResponseEnvelope<UUID> result = new ResponseEnvelope<>("200", gameUuid, "성공입니다.");
        return result;
    }

    @PostMapping("/{gameId}/enter")
    public ResponseEnvelope<GameRoomDetailRes> enterGameRoom(@PathVariable("gameId") long gameId) {
        GameRoom gameRoom = gameRoomService.enterGameRoom(gameId);
        GameRoomDetailRes gameRoomDetailRes = new GameRoomDetailRes(gameRoom);
        ResponseEnvelope<GameRoomDetailRes> result = new ResponseEnvelope<>("200", gameRoomDetailRes, "성공입니다.");
        return result;
    }

    @PostMapping("/{gameId}/exit")
    public ResponseEnvelope<GameRoomDetailRes> exitGameRoom(@PathVariable("gameId") long gameId) {
        GameRoom gameRoom = gameRoomService.exitGameRoom(gameId);
        GameRoomDetailRes gameRoomDetailRes = new GameRoomDetailRes(gameRoom);
        ResponseEnvelope<GameRoomDetailRes> result = new ResponseEnvelope<>("200", gameRoomDetailRes, "성공입니다.");
        return result;
    }

    @DeleteMapping("/{gameId}")
    public ResponseEnvelope<Long> deleteGameRoom(@PathVariable("gameId") long gameId) {
        gameRoomService.deleteGameRoom(gameId);
        ResponseEnvelope<Long> result = new ResponseEnvelope<>("200", gameId, "성공입니다.");
        return result;
    }


}
