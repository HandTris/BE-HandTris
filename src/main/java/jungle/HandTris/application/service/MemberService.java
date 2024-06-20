package jungle.HandTris.application.service;

import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void signin (MemberRequest memberRequest) {
        String username = memberRequest.getUsername();
        String password = memberRequest.getPassword();

        Member member = memberRepository.findByUsername(username);
        // 비밀번호 확인

        if(!member.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
