package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.MemberRequest;
import org.springframework.data.util.Pair;

public interface MemberService {
    Pair<Long, String> signin(MemberRequest memberRequest);

    void signup(MemberRequest memberRequest);

}
