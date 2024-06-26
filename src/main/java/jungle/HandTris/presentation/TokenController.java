package jungle.HandTris.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.application.service.MemberInfoService;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    private final MemberInfoService memberInfoService;

    @GetMapping("/member/info")
    public ResponseEnvelope<MemberDetailRes> getMemberInfoToToken(HttpServletRequest request) {
        MemberDetailRes memberDetail = memberInfoService.getMemberInfo(request);

        return ResponseEnvelope.of(memberDetail);
    }
}
