package jungle.HandTris.application.service;

import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.exception.CustomException;
import jungle.HandTris.domain.repo.MemberRepository;
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
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void signup(MemberRequest memberRequest) {
        boolean isUsername = memberRepository.existsByUsername(memberRequest.username());
        boolean isNickname = memberRepository.existsByNickname(memberRequest.nickname());

        if (isNickname) {
            throw new CustomException("이미 존재하는 닉네임 입니다.");
        }

        if (isUsername) {
            throw new CustomException("이미 존재하는 ID 입니다.");
        }

        String username = memberRequest.username();
        String password = memberRequest.password();
        String nickname = memberRequest.nickname();

        Member data = new Member(username, bCryptPasswordEncoder.encode(password), nickname);

        memberRepository.save(data);
    }
}
