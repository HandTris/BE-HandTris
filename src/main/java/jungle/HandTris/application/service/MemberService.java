package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.MemberRequest;
import org.springframework.data.util.Pair;

public interface MemberService {
    public Pair<Long, String> signin(MemberRequest memberRequest);

    public void signup(MemberRequest memberRequest);

}
