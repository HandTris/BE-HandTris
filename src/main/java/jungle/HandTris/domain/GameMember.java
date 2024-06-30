package jungle.HandTris.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.LinkedHashSet;
import java.util.Set;

@RedisHash(value = "gameMember")
@Getter
public class GameMember {
    @Id
    private String id;

    private Set<String> members = new LinkedHashSet<>(); // HashSet -> Linked HashSet

    public GameMember(String id) {
        this.id = id;
    }

    public void addMember(String MemberId) {
        this.members.add(MemberId); // this 붙여야 자기 접근자가 멀티 스레드 환경에서 정상적으로 동작
    }

    public void removeMember(String MemberId) {
        this.members.remove(MemberId);
    }
}
