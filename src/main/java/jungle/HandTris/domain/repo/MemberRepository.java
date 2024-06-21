package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {
    Member findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Member save(Member member);
}
