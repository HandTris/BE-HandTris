package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.MemberRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecordRepository extends JpaRepository<MemberRecord, Long> {
}
