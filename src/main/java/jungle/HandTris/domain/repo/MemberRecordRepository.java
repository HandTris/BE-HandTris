package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.MemberRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRecordRepository extends JpaRepository<MemberRecord, Long> {
    Optional<MemberRecord> findByMember(Member member);
}
