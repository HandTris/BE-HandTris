package jungle.HandTris.presentation.controller;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEnvelope<String> signup(@RequestBody @Valid MemberRequest memberRequest) {
        memberService.signup(memberRequest);

        return ResponseEnvelope.of("Signup Successful");
    }

    @PostMapping("/signin")
    public ResponseEnvelope<String> signin(@RequestBody MemberRequest memberRequest) {
        memberService.signin(memberRequest);

        return ResponseEnvelope.of("Login Successful");
    }
}
