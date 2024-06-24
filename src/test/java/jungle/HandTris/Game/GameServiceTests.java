package jungle.HandTris.Game;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jungle.HandTris.application.impl.GameServiceImpl;
import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.exception.GameNotFoundException;
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
        GameDetailReq gameDetailReq1 = new @Valid GameDetailReq("HANDTRIS", 2);
        GameDetailReq gameDetailReq2 = new @Valid GameDetailReq("HANDTRIS", 3);
        Game game1 = new Game(gameDetailReq1);
        Game game2 = new Game(gameDetailReq2);
        gameRepository.save(game1);
        gameRepository.save(game2);

        /* when : 실제 테스트 실행 */
        List<Game> actualGameList = gameServiceImpl.getGameList();

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(actualGameList).isNotNull();
        Assertions.assertThat(actualGameList).hasSizeGreaterThan(2);
        Assertions.assertThat(actualGameList.contains(game1)).isTrue();
        Assertions.assertThat(actualGameList.contains(game2)).isTrue();
    }

    // not in mockito test
    @Test
    @DisplayName("게임 생성 Test")
    void createGameTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);

        /* when : 실제 테스트 실행 */
        UUID gameUuid = gameServiceImpl.createGame(gameDetailReq);

        /* then : 테스트 결과 검증 */
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

        /* when : 실제 테스트 실행 */
        Game enteredGame = gameServiceImpl.enterGame(gameId);

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(enteredGame).isNotNull();
        Assertions.assertThat(enteredGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(enteredGame.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(enteredGame.getParticipantCount()).isEqualTo(beforeParticipantCount + 1);
    }

    @Test
    @DisplayName("플레이어의 게임 나가기 Test")
    void exitGameByPlayerTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        newgame.enter(); // 게임 임장
        gameRepository.save(newgame);

        /* when : 실제 테스트 실행 */
        Game exitedGame = gameServiceImpl.exitGame(newgame.getId());

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(exitedGame).isNotNull();
        Assertions.assertThat(exitedGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(exitedGame.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(exitedGame.getParticipantCount()).isEqualTo(1);
    }

    // not in mockito test
    @Test
    @DisplayName("방장의 게임 나가기 Test")
    void exitGameByOwnerTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        gameRepository.save(newgame);

        /* when : 실제 테스트 실행 */
        Game exitedGame = gameServiceImpl.exitGame(newgame.getId());

        /* then : 테스트 결과 검증 */
        // 삭제된 Game 검증
        Assertions.assertThat(exitedGame).isNotNull();
        Assertions.assertThat(exitedGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(exitedGame.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(exitedGame.getParticipantCount()).isEqualTo(0);
        // 삭제 여부 검증
        Assertions.assertThatThrownBy(() -> gameRepository.findById(exitedGame.getId()).orElseThrow(GameNotFoundException::new))
                .isInstanceOf(GameNotFoundException.class);
    }

    // not in mockito test
    @Test
    @DisplayName("게임 삭제 Test")
    void deleteGameTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        gameRepository.save(newgame);

        /* when : 실제 테스트 실행 */
        gameServiceImpl.deleteGame(newgame.getId());

        /* then : 테스트 결과 검증 */
        Assertions.assertThatThrownBy(() -> gameRepository.findById(newgame.getId()).orElseThrow(GameNotFoundException::new))
                .isInstanceOf(GameNotFoundException.class);
    }


}
