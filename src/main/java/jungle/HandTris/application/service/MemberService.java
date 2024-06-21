package jungle.HandTris.application.service;

import jakarta.transaction.Transactional;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.MemberRecord;
import jungle.HandTris.domain.exception.MemberNotFoundException;
import jungle.HandTris.domain.exception.MemberRecordNodFoundException;
import jungle.HandTris.domain.repo.MemberRecordRepository;
import jungle.HandTris.domain.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRecordRepository memberRecordRepository;
    private final MemberRepository memberRepository;

    public MemberRecord getMemberRecord(String memberNickname) {

        Optional<Member> member = memberRepository.findByNickname(memberNickname);
        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }
        Optional<MemberRecord> memberRecord = memberRecordRepository.findByMember(member.get());
        if (memberRecord.isEmpty()) {
            throw new MemberRecordNodFoundException();
        }
        return memberRecord.get();
    }
}
