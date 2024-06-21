package jungle.HandTris.application.service;

import jakarta.transaction.Transactional;
import jungle.HandTris.domain.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

}
