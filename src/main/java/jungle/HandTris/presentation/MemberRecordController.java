package jungle.HandTris.presentation;

import jungle.HandTris.application.service.MemberRecordService;
import jungle.HandTris.domain.MemberRecord;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
