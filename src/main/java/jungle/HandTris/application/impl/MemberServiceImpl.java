package jungle.HandTris.application.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.MemberRecord;
import jungle.HandTris.domain.exception.*;
import jungle.HandTris.domain.repo.MemberRecordRepository;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.global.jwt.JWTUtil;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberRecordRepository memberRecordRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    @Value("${cloud.aws.s3.defaultImage}")
    private String defaultImage;

    @Override
    public Pair<Member, String> signin (MemberRequest memberRequest) {
        String username = memberRequest.username();
        String password = memberRequest.password();

        Member member = Optional.ofNullable(memberRepository.findByUsername(username))
                .orElseThrow(() -> new UserNotFoundException());

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new PasswordMismatchException();
        }

        String accessToken = jwtUtil.createAccessToken(member.getNickname());
        String refreshToken = jwtUtil.createRefreshToken();

        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        return Pair.of(member, accessToken);
    }

    @Override
    public void signup(MemberRequest memberRequest) {
        boolean usernameExists = memberRepository.existsByUsername(memberRequest.username());
        boolean nicknameExists = memberRepository.existsByNickname(memberRequest.nickname());

        if (nicknameExists) {
            throw new DuplicateNicknameException();
        }

        if (usernameExists) {
            throw new DuplicateUsernameException();
        }

        String username = memberRequest.username();
        String password = memberRequest.password();
        String nickname = memberRequest.nickname();

        Member data = new Member(username, bCryptPasswordEncoder.encode(password), nickname);
        data.updateProfileImageUrl(defaultImage);
        MemberRecord memberRecord = new MemberRecord(data);

        memberRepository.save(data);
        memberRecordRepository.save(memberRecord);
    }

    @Override
    public void signout(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveAccessToken(request);

        if (jwtUtil.isExpired(accessToken)) {
            throw new AccessTokenExpiredException();
        }

        String nickname = jwtUtil.getNickname(accessToken);

        memberRepository.findByNickname(nickname)
                .ifPresent(Member::deleteRefreshToken);
    }
}
