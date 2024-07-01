package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.MemberRecord;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRecordRepository extends Repository<MemberRecord, Long> {
    Optional<MemberRecord> findByMember(Member member);

    MemberRecord save(MemberRecord memberRecord);
}
