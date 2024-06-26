package jungle.HandTris.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailResWithTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEnvelope<String> signup(@RequestBody @Valid MemberRequest memberRequest) {
        memberService.signup(memberRequest);

        return ResponseEnvelope.of("Signup Successful");
    }

    @PostMapping("/signin")
    public ResponseEnvelope<MemberDetailResWithTokenRes> signin(@RequestBody MemberRequest memberRequest) {
        Pair<Member, String> result = memberService.signin(memberRequest);

        Member member = result.getFirst();
        String accessToken = result.getSecond();

        MemberDetailResWithTokenRes memberDetailResWithTokenRes = new MemberDetailResWithTokenRes(
                member.getUsername(),
                member.getNickname(),
                accessToken,
                member.getRefreshToken()
        );

        return ResponseEnvelope.of(memberDetailResWithTokenRes);
    }

    @GetMapping("/signout")
    public ResponseEnvelope<String> signout(HttpServletRequest request) {
        memberService.signout(request);

        return ResponseEnvelope.of("Signout Successful");
    }
}
