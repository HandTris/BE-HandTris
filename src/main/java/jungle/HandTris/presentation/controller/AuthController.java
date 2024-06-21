package jungle.HandTris.presentation.controller;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberIdDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEnvelope<MemberIdDetails> signin(@RequestBody MemberRequest memberRequest) {
        Long memberId = memberService.signin(memberRequest);

        return ResponseEnvelope.of(new MemberIdDetails(memberId));
    }
}
