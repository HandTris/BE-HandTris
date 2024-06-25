package jungle.HandTris.presentation;

import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.global.jwt.JWTUtil;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEnvelope<String> signup(@RequestBody @Valid MemberRequest memberRequest) {
        memberService.signup(memberRequest);

        return ResponseEnvelope.of("Signup Successful");
    }

    @PostMapping("/signin")
    public ResponseEntity<MemberDetailRes> signin(@RequestBody MemberRequest memberRequest) {
        Pair<Member, String> result = memberService.signin(memberRequest);

        Member member = result.getFirst();
        String accessToken = result.getSecond();
        String refreshToken = member.getRefreshToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(jwtUtil.accessHeader, accessToken);
        headers.add(jwtUtil.refreshHeader, refreshToken);

        MemberDetailRes memberDetailRes = new MemberDetailRes(member);


        return ResponseEntity.ok()
                .headers(headers)
                .body(memberDetailRes);
    }
}
