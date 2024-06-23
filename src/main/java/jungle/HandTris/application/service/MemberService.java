package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import org.springframework.data.util.Pair;

public interface MemberService {
    Pair<MemberDetailRes, String> signin(MemberRequest memberRequest);

    void signup(MemberRequest memberRequest);

}
