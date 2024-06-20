package jungle.HandTris.application.service;

import jungle.HandTris.domain.Member;
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
        String username = memberRequest.getUsername();
        String password = memberRequest.getPassword();

        Member member = memberRepository.findByUsername(username);

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void signup(MemberRequest memberRequest) {
        boolean isUser = memberRepository.existsByUsername(memberRequest.getUsername());
        if (isUser) {
            throw new IllegalArgumentException("이미 존재하는 ID 입니다.");
        }

        Member data = new Member();

        data.setUsername(memberRequest.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(memberRequest.getPassword()));
        data.setNickname(memberRequest.getNickname());

        memberRepository.save(data);
    }
}
