package jungle.HandTris.Game;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jungle.HandTris.application.impl.GameServiceImpl;
import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.repo.GameRepository;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional //테스트 과정중 생긴 변화가 실제 DB에 반영하지 않도록 설정
public class GameServiceTests {

    @Autowired
    private GameServiceImpl gameServiceImpl;
    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("게임 목록 조회 Test")
    void getGameListTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        gameRepository.save(newgame);

        /* when : 실제 테스트 실행*/
        List<Game> actualGameList = gameServiceImpl.getGameList();

        /* then : 테스트 결과 검증*/
        for (Game game : actualGameList) {
            System.out.println(game.getGameCategory() + " " + game.getParticipantLimit());
        }
        Assertions.assertThat(actualGameList).isNotNull();
        Assertions.assertThat(actualGameList).hasSizeGreaterThan(1);
    }

    @Test
    @DisplayName("게임 생성 Test")
    void createGameTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);

        /* when : 실제 테스트 실행*/
        UUID gameUuid = gameServiceImpl.createGame(gameDetailReq);

        /* then : 테스트 결과 검증*/
        Game createdGame = gameRepository.findByUuid(gameUuid).orElse(null);
        Assertions.assertThat(createdGame).isNotNull();
        Assertions.assertThat(createdGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(createdGame.getParticipantLimit()).isEqualTo(3);
    }

    @Test
    @DisplayName("게임 입장 Test")
    void enterGameTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        long beforeParticipantCount = newgame.getParticipantCount();
        gameRepository.save(newgame);
        long gameId = newgame.getId();

        /* when : 실제 테스트 실행*/
        Game enteredGame = gameServiceImpl.enterGame(gameId);

        /* then : 테스트 결과 검증*/
        Assertions.assertThat(enteredGame).isNotNull();
        Assertions.assertThat(enteredGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(enteredGame.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(enteredGame.getParticipantCount()).isEqualTo(beforeParticipantCount + 1);
    }


}
