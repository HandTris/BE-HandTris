package jungle.HandTris.application.service;

import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.exception.CustomException;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.global.exception.ErrorCode;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signin (MemberRequest memberRequest) {
        String username = memberRequest.username();
        String password = memberRequest.password();

        Member member = memberRepository.findByUsername(username);

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public void signup(MemberRequest memberRequest) {
        boolean isUsername = memberRepository.existsByUsername(memberRequest.username());
        boolean isNickname = memberRepository.existsByNickname(memberRequest.nickname());

        if (isNickname) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if (isUsername) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        String username = memberRequest.username();
        String password = memberRequest.password();
        String nickname = memberRequest.nickname();

        Member data = new Member(username, bCryptPasswordEncoder.encode(password), nickname);

        memberRepository.save(data);
    }
}
