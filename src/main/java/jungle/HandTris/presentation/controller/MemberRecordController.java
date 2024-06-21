package jungle.HandTris.presentation.controller;

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
    public ResponseEnvelope<MemberRecordDetailRes> getMemberRecord(@PathVariable("nickname") String memberNickname) {
        MemberRecord memberRecord = memberRecordService.getMemberRecord(memberNickname);
        MemberRecordDetailRes memberRecordDetailRes = new MemberRecordDetailRes(memberRecord);
        return new ResponseEnvelope<>("200", memberRecordDetailRes, "성공입니다.");
    }

}
