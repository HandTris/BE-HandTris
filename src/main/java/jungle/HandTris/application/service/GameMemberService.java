package jungle.HandTris.application.service;

import jungle.HandTris.domain.GameMember;
import jungle.HandTris.domain.exception.ConnectedMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GameMemberService { //DIP 를 위해 Interface 분리해주세요 -> 아직 테스트 파일이라 추후에 진행
    private final GameMemberRepo gameMemberRepo;

    public Set<String> getUsersByRoomCode(String roomCode) {
        Optional<GameMember> gameMember = gameMemberRepo.findById(roomCode);
        // gameMember 객체가 존재하는 경우 getMembers() 메소드를 호출하여 Set<String> 반환 (존재하지 않는 경우 예외처리)
        return gameMember.map(GameMember::getMembers).orElseThrow(() -> new ConnectedMemberNotFoundException()); //TODO Customable한 Exception 터트리도록 변경
    }

    public void addMemberToRoom(String roomCode, String MemberId) {
        // 인자값으로 받은 roomCode와 같은 GameMember 객체를 찾고, 존재하지 않으면 생성
        GameMember gameMember = gameMemberRepo.findById(roomCode).orElse(new GameMember(roomCode));
        gameMember.addMember(MemberId);
        gameMemberRepo.save(gameMember);
    }

    public void removeMemberFromRoom(String roomCode, String MemberId) {
        GameMember gameMember = gameMemberRepo.findById(roomCode).orElseThrow(() -> new ConnectedMemberNotFoundException());//TODO Customable한 Exception 터트리도록 변경
        gameMember.removeMember(MemberId);
        if (gameMember.getMembers().isEmpty()) {
            // 방에 아무도 없으면 방 삭제
            gameMemberRepo.delete(gameMember);
        } else {
            // 방에 누군가 남아있으면 업데이트
            gameMemberRepo.deleteById(roomCode);
            gameMemberRepo.save(gameMember); // TODO REDIS에선 업데이트 로직을 짜기 위해선 삭제 후 재저장이 권장됩니다. https://learngoeson.tistory.com/54
        }
    }

    public void deleteRoom(String roomCode) {
        gameMemberRepo.deleteById(roomCode);
    }
}

