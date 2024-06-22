package jungle.HandTris.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberIdDetails;
import lombok.RequiredArgsConstructor;
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
    public ResponseEnvelope<MemberIdDetails> signin(@RequestBody MemberRequest memberRequest, HttpServletResponse response) {
        Long memberId = memberService.signin(memberRequest, response);


        return ResponseEnvelope.of(new MemberIdDetails(memberId));
    }
}
