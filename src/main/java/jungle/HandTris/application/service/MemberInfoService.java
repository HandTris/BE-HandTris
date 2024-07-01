package jungle.HandTris.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.presentation.dto.request.MemberUpdateReq;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import jungle.HandTris.presentation.dto.response.MemberInfoDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberInfoUpdateDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public interface MemberInfoService {
    MemberDetailRes getMemberInfo(HttpServletRequest request);

    Pair<MemberInfoDetailsRes, MemberRecordDetailRes> myPage(HttpServletRequest request, String username);

    MemberInfoUpdateDetailsRes updateInfo(HttpServletRequest request, MemberUpdateReq memberUpdateReq, MultipartFile profileImage, Boolean deleteProfileImage, String username);
}
