package jungle.HandTris.Game;

import jakarta.validation.Valid;
import jungle.HandTris.application.impl.GameServiceImpl;
import jungle.HandTris.domain.Game;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.repo.GameRepository;
import jungle.HandTris.presentation.dto.request.GameDetailReq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

// junit5에서 Mockito를 사용하기 위해 붙이는 어노테이션, Service 영역에 대한 단위 테스트를 위해서 사용
@ExtendWith(MockitoExtension.class)
public class MockGameServiceTests {

    // 테스트를 위한 가짜 객체 생성
    @Mock
    private GameRepository gameRepository;

    // 생성시 Mock으로 만든 가짜 객체들을 주입받는 객체
    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Test
    @DisplayName("게임 목록 조회 Test")
    void getGameListTest() {
        /* given : 테스트 사전 조건 설정 */
        // entity 생성을 위한 DTO도 실제 코드와 같이 @Valid로 입력값 검사
        GameDetailReq gameDetailReq1 = new @Valid GameDetailReq("HANDTRIS", 2);
        GameDetailReq gameDetailReq2 = new @Valid GameDetailReq("HANDTRIS", 3);
        Game game1 = new Game(gameDetailReq1);
        Game game2 = new Game(gameDetailReq2);
        List<Game> expectedGameList = Arrays.asList(game1, game2);

        // Repository 객체의 동작을 정의
        when(gameRepository.findAllByGameStatusNotPlaying()).thenReturn(expectedGameList);

        /* when */
        List<Game> actualGameList = gameServiceImpl.getGameList();

        /* then */
        for (Game game : actualGameList) {
            System.out.println(game.getGameCategory() + " " + game.getParticipantLimit());
        }

        Assertions.assertThat(actualGameList).isNotNull();
        Assertions.assertThat(actualGameList.size()).isEqualTo(expectedGameList.size());
        Assertions.assertThat(actualGameList).containsExactlyInAnyOrderElementsOf(expectedGameList);
    }

    @Test
    @DisplayName("게임 입장 Test")
    void enterGameTest() {
        /* given : 테스트 사전 조건 설정 */
        GameDetailReq gameDetailReq = new @Valid GameDetailReq("HANDTRIS", 3);
        Game newgame = new Game(gameDetailReq);
        long beforeParticipantCount = newgame.getParticipantCount();
        long gameId = newgame.getId();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(newgame));

        /* when : 실제 테스트 실행*/
        Game enteredGame = gameServiceImpl.enterGame(gameId);

        /* then : 테스트 결과 검증*/
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
        long gameId = newgame.getId();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(newgame));

        /* when : 실제 테스트 실행 */
        Game exitedGame = gameServiceImpl.exitGame(newgame.getId());

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(exitedGame).isNotNull();
        Assertions.assertThat(exitedGame.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(exitedGame.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(exitedGame.getParticipantCount()).isEqualTo(1);
    }


}