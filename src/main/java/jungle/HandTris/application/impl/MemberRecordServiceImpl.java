package jungle.HandTris.application.impl;

import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.MemberRecordService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.MemberRecord;
import jungle.HandTris.domain.exception.MemberNotFoundException;
import jungle.HandTris.domain.exception.MemberRecordNotFoundException;
import jungle.HandTris.domain.repo.MemberRecordRepository;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.presentation.dto.request.GameResult;
import jungle.HandTris.presentation.dto.request.GameResultReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberRecordServiceImpl implements MemberRecordService {

    private final MemberRecordRepository memberRecordRepository;
    private final MemberRepository memberRepository;

    public MemberRecord getMemberRecord(String nickname) {

        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }
        Optional<MemberRecord> memberRecord = memberRecordRepository.findByMember(member.get());
        if (memberRecord.isEmpty()) {
            throw new MemberRecordNotFoundException();
        }
        return memberRecord.get();
    }

    public MemberRecord updateMemberRecord(GameResultReq gameResultReq, String nickname) {
        GameResult gameResult = GameResult.valueOf(GameResult.class, gameResultReq.gameResult());

        // jwt로 정보 받아온 정보로 getMemberRecord 찾기
        MemberRecord memberRecord = getMemberRecord(nickname);

        // gameResult에 따라 업데이트
        if (GameResult.valueOf(GameResult.class, gameResultReq.gameResult()).equals(GameResult.WIN)) {
            memberRecord.win();
        } else if (GameResult.valueOf(GameResult.class, gameResultReq.gameResult()).equals(GameResult.LOSE)) {
            memberRecord.lose();
        }
        return memberRecord;
    }
}
