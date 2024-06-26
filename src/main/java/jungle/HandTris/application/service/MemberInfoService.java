package jungle.HandTris.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;

public interface MemberInfoService {
    MemberDetailRes getMemberInfo(HttpServletRequest request);
}
