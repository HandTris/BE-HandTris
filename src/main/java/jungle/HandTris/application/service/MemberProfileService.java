package jungle.HandTris.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.presentation.dto.request.MemberUpdateReq;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import jungle.HandTris.presentation.dto.response.MemberProfileDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberProfileUpdateDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public interface MemberProfileService {
    MemberDetailRes loadMemberProfileByToken(HttpServletRequest request);

    Pair<MemberProfileDetailsRes, MemberRecordDetailRes> myPage(HttpServletRequest request, String username);

    MemberProfileUpdateDetailsRes updateMemberProfile(HttpServletRequest request, MemberUpdateReq memberUpdateReq, MultipartFile profileImage, Boolean deleteProfileImage, String username);
}
