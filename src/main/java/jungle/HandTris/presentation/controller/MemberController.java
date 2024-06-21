package jungle.HandTris.presentation.controller;

import jungle.HandTris.application.service.MemberService;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberNickname}/record")
    public ResponseEnvelope<MemberRecordDetailRes> getMemberRecord(@PathVariable("memberNickname") String memberNickname) {
        MemberRecord memberRecord = memberService.getMemberRecord(memberNickname);
        MemberRecordDetailRes memberRecordDetailRes = new MemberRecordDetailRes(memberRecord);
        return new ResponseEnvelope<>("200", memberRecordDetailRes, "성공입니다.");
    }
}