package jungle.HandTris.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jungle.HandTris.application.service.MemberProfileService;
import jungle.HandTris.global.dto.ResponseEnvelope;
import jungle.HandTris.presentation.dto.request.MemberUpdateReq;
import jungle.HandTris.presentation.dto.response.MemberProfileDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberProfileUpdateDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberProfileService memberProfileService;

    @GetMapping("/{username}/mypage")
    public ResponseEnvelope<Pair<MemberProfileDetailsRes, MemberRecordDetailRes>> myPage(HttpServletRequest request, @PathVariable("username") String username) {
        Pair<MemberProfileDetailsRes, MemberRecordDetailRes> result = memberProfileService.myPage(request, username);

        return ResponseEnvelope.of(result);
    }

    @PatchMapping("/{username}/mypage")
    public ResponseEnvelope<MemberProfileUpdateDetailsRes> updateInfo(
            HttpServletRequest request,
            @Valid MemberUpdateReq memberUpdateReq,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "deleteProfileImage", required = false, defaultValue = "false") boolean deleteProfileImage,
            @PathVariable("username") String username) {

        MemberProfileUpdateDetailsRes updateMemberDetails = memberProfileService.updateMemberProfile(request, memberUpdateReq, profileImage, deleteProfileImage, username);

        return ResponseEnvelope.of(updateMemberDetails);
    }
}
