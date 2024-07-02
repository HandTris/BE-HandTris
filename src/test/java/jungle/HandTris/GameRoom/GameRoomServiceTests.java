package jungle.HandTris.GameRoom;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jungle.HandTris.application.impl.GameRoomServiceImpl;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.domain.GameRoom;
import jungle.HandTris.domain.exception.GameRoomNotFoundException;
import jungle.HandTris.domain.repo.GameRoomRepository;
import jungle.HandTris.presentation.dto.request.GameRoomDetailReq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional //테스트 과정중 생긴 변화가 실제 DB에 반영하지 않도록 설정
public class GameRoomServiceTests {

    @Autowired
    private GameRoomServiceImpl gameServiceImpl;
    @Autowired
    private GameRoomRepository gameRoomRepository;

    @Test
    @DisplayName("게임 목록 조회 Test")
    void getGameRoomListTest() {
        /* given : 테스트 사전 조건 설정 */
        GameRoomDetailReq gameRoomDetailReq1 = new @Valid GameRoomDetailReq("HANDTRIS", 2);
        GameRoomDetailReq gameRoomDetailReq2 = new @Valid GameRoomDetailReq("HANDTRIS", 3);
        GameRoom gameRoom1 = new GameRoom(gameRoomDetailReq1);
        GameRoom gameRoom2 = new GameRoom(gameRoomDetailReq2);
        gameRoomRepository.save(gameRoom1);
        gameRoomRepository.save(gameRoom2);

        /* when : 실제 테스트 실행 */
        List<GameRoom> actualGameRoomList = gameServiceImpl.getGameRoomList();

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(actualGameRoomList).isNotNull();
        Assertions.assertThat(actualGameRoomList).hasSizeGreaterThan(1);
        Assertions.assertThat(actualGameRoomList.contains(gameRoom1)).isTrue();
        Assertions.assertThat(actualGameRoomList.contains(gameRoom2)).isTrue();
    }

    // not in mockito test
    @Test
    @DisplayName("게임 생성 Test")
    void createGameRoomTest() {
        /* given : 테스트 사전 조건 설정 */
        GameRoomDetailReq gameRoomDetailReq = new @Valid GameRoomDetailReq("HANDTRIS", 3);

        /* when : 실제 테스트 실행 */
        UUID gameUuid = gameServiceImpl.createGameRoom(gameRoomDetailReq);

        /* then : 테스트 결과 검증 */
        GameRoom createdGameRoom = gameRoomRepository.findByRoomNumber(gameUuid).orElse(null);
        Assertions.assertThat(createdGameRoom).isNotNull();
        Assertions.assertThat(createdGameRoom.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(createdGameRoom.getParticipantLimit()).isEqualTo(3);
    }

    @Test
    @DisplayName("게임 입장 Test")
    void enterGameRoomTest() {
        /* given : 테스트 사전 조건 설정 */
        GameRoomDetailReq gameRoomDetailReq = new @Valid GameRoomDetailReq("HANDTRIS", 3);
        GameRoom newgame = new GameRoom(gameRoomDetailReq);
        long beforeParticipantCount = newgame.getParticipantCount();
        gameRoomRepository.save(newgame);
        String gameUuid = newgame.getRoomNumber().toString();

        /* when : 실제 테스트 실행 */
        GameRoom enteredGameRoom = gameServiceImpl.enterGameRoom(gameUuid);

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(enteredGameRoom).isNotNull();
        Assertions.assertThat(enteredGameRoom.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(enteredGameRoom.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(enteredGameRoom.getParticipantCount()).isEqualTo(beforeParticipantCount + 1);
    }

    @Test
    @DisplayName("플레이어의 게임 나가기 Test")
    void exitGameRoomByPlayerTest() {
        /* given : 테스트 사전 조건 설정 */
        GameRoomDetailReq gameRoomDetailReq = new @Valid GameRoomDetailReq("HANDTRIS", 3);
        GameRoom newgame = new GameRoom(gameRoomDetailReq);
        newgame.enter(); // 게임 임장
        gameRoomRepository.save(newgame);

        /* when : 실제 테스트 실행 */
        GameRoom exitedGameRoom = gameServiceImpl.exitGameRoom(newgame.getRoomNumber().toString());

        /* then : 테스트 결과 검증 */
        Assertions.assertThat(exitedGameRoom).isNotNull();
        Assertions.assertThat(exitedGameRoom.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(exitedGameRoom.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(exitedGameRoom.getParticipantCount()).isEqualTo(1);
    }

    // not in mockito test
    @Test
    @DisplayName("방장의 게임 나가기 Test")
    void exitGameRoomByOwnerTest() {
        /* given : 테스트 사전 조건 설정 */
        GameRoomDetailReq gameRoomDetailReq = new @Valid GameRoomDetailReq("HANDTRIS", 3);
        GameRoom newgame = new GameRoom(gameRoomDetailReq);
        gameRoomRepository.save(newgame);

        /* when : 실제 테스트 실행 */
        GameRoom exitedGameRoom = gameServiceImpl.exitGameRoom(newgame.getRoomNumber().toString());

        /* then : 테스트 결과 검증 */
        // 삭제된 Game 검증
        Assertions.assertThat(exitedGameRoom).isNotNull();
        Assertions.assertThat(exitedGameRoom.getGameCategory()).isEqualTo(GameCategory.HANDTRIS);
        Assertions.assertThat(exitedGameRoom.getParticipantLimit()).isEqualTo(3);
        Assertions.assertThat(exitedGameRoom.getParticipantCount()).isEqualTo(0);
        // 삭제 여부 검증
        Assertions.assertThatThrownBy(() -> gameRoomRepository.findById(exitedGameRoom.getId()).orElseThrow(GameRoomNotFoundException::new))
                .isInstanceOf(GameRoomNotFoundException.class);
    }

}
