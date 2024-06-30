package jungle.HandTris.presentation;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberRecordService;
import jungle.HandTris.domain.MemberRecord;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.global.validation.UserNicknameFromJwt;
import jungle.HandTris.presentation.dto.request.GameResultReq;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class MemberRecordController {

    private final MemberRecordService memberRecordService;

    @GetMapping("/{nickname}")
    public ResponseEnvelope<MemberRecordDetailRes> getMemberRecord(@PathVariable("nickname") String nickname) {
        MemberRecord memberRecord = memberRecordService.getMemberRecord(nickname);
        MemberRecordDetailRes memberRecordDetailRes = new MemberRecordDetailRes(memberRecord);
        return ResponseEnvelope.of(memberRecordDetailRes);
    }

    @PutMapping
    public ResponseEnvelope<MemberRecordDetailRes> updateMemberRecord(@Valid @RequestBody GameResultReq gameResultReq, @UserNicknameFromJwt String nickname) {
        MemberRecord memberRecord = memberRecordService.updateMemberRecord(gameResultReq, nickname);
        MemberRecordDetailRes memberRecordDetailRes = new MemberRecordDetailRes(memberRecord);
        return ResponseEnvelope.of(memberRecordDetailRes);
    }

}
