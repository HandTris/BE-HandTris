package jungle.HandTris.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.domain.Member;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import org.springframework.data.util.Pair;

public interface MemberService {
    Pair<Member, String> signin(MemberRequest memberRequest);

    void signup(MemberRequest memberRequest);

    void signout(HttpServletRequest request);

}
