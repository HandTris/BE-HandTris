package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Optional<Member> findByNickname(String nickname);
}
