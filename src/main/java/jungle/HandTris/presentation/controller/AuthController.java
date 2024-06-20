package jungle.HandTris.presentation.controller;

import jungle.HandTris.application.service.MemberService;
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

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody MemberRequest memberRequest) {
        memberService.signin(memberRequest);

        return ResponseEntity.ok("Login Successful");
    }
}
