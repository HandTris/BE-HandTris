package jungle.HandTris.application.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.MemberInfoService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.domain.exception.*;
import jungle.HandTris.global.jwt.JWTUtil;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDetailRes getMemberInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);

        String nickname = jwtUtil.getNickname(accessToken);

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);

        String username = member.getUsername();
        return new MemberDetailRes(username, nickname);
    }
}
