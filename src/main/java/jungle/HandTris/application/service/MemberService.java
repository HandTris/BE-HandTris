package jungle.HandTris.application.service;

import jungle.HandTris.presentation.dto.request.MemberRequest;

public interface MemberService {
    public Long signin(MemberRequest memberRequest);

    public void signup(MemberRequest memberRequest);

}
