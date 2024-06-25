package jungle.HandTris.presentation;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailResWithToken;
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
    public ResponseEnvelope<MemberDetailResWithToken> signin(@RequestBody MemberRequest memberRequest) {
        Pair<Member, String> result = memberService.signin(memberRequest);

        Member member = result.getFirst();
        String accessToken = result.getSecond();

        MemberDetailResWithToken memberDetailResWithToken = new MemberDetailResWithToken(
                member.getUsername(),
                member.getNickname(),
                accessToken,
                member.getRefreshToken()
        );

        return ResponseEnvelope.of(memberDetailResWithToken);
    }
}
