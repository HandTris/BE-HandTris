package jungle.HandTris.presentation;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.GameService;
import jungle.HandTris.domain.Game;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import jungle.HandTris.presentation.dto.response.GameDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;


    @GetMapping
    public ResponseEnvelope<List<GameDetailRes>> getGameList() {
        List<GameDetailRes> gameDetailResList = gameService.getGameList().stream().map(GameDetailRes::new).toList();
        ResponseEnvelope<List<GameDetailRes>> result = new ResponseEnvelope<>("200", gameDetailResList, "성공입니다.");
        return result;
    }

    @PostMapping
    public ResponseEnvelope<UUID> createGame(@Valid @RequestBody GameDetailReq gameDetailReq) {
        UUID gameUuid = gameService.createGame(gameDetailReq);
        ResponseEnvelope<UUID> result = new ResponseEnvelope<>("200", gameUuid, "성공입니다.");
        return result;
    }

    @PostMapping("/{gameId}/enter")
    public ResponseEnvelope<GameDetailRes> enterGame(@PathVariable("gameId") long gameId) {
        Game game = gameService.enterGame(gameId);
        GameDetailRes gameDetailRes = new GameDetailRes(game);
        ResponseEnvelope<GameDetailRes> result = new ResponseEnvelope<>("200", gameDetailRes, "성공입니다.");
        return result;
    }

    @PostMapping("/{gameId}/exit")
    public ResponseEnvelope<GameDetailRes> exitGame(@PathVariable("gameId") long gameId) {
        Game game = gameService.exitGame(gameId);
        GameDetailRes gameDetailRes = new GameDetailRes(game);
        ResponseEnvelope<GameDetailRes> result = new ResponseEnvelope<>("200", gameDetailRes, "성공입니다.");
        return result;
    }

    @DeleteMapping("/{gameId}")
    public ResponseEnvelope<Long> deleteGame(@PathVariable("gameId") long gameId) {
        gameService.deleteGame(gameId);
        ResponseEnvelope<Long> result = new ResponseEnvelope<>("200", gameId, "성공입니다.");
        return result;
    }


}
