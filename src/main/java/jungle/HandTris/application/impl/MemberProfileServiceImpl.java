package jungle.HandTris.application.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jungle.HandTris.application.service.MemberProfileService;
import jungle.HandTris.application.service.MemberRecordService;
import jungle.HandTris.application.service.S3UploaderService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.exception.*;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.global.jwt.JWTUtil;
import jungle.HandTris.presentation.dto.request.MemberUpdateReq;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import jungle.HandTris.presentation.dto.response.MemberProfileDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberProfileUpdateDetailsRes;
import jungle.HandTris.presentation.dto.response.MemberRecordDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberProfileServiceImpl implements MemberProfileService {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final MemberRecordService memberRecordService;
    private final S3UploaderService s3UploaderService;

    @Override
    public MemberDetailRes loadMemberProfileByToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);

        String nickname = jwtUtil.getNickname(accessToken);

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);

        String username = member.getUsername();
        return new MemberDetailRes(username, nickname);
    }

    @Override
    public Pair<MemberProfileDetailsRes, MemberRecordDetailRes> myPage(HttpServletRequest request, String username) {
        String token = jwtUtil.resolveAccessToken(request);
        String nickname = jwtUtil.getNickname(token);
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new);

        // 요청한 유저와 토큰의 주인이 같은지 검증
        if (!username.equals(member.getUsername())) {
            throw new UnauthorizedAccessException();
        }

        MemberProfileDetailsRes memberInfoDetails = new MemberProfileDetailsRes(member.getNickname(), member.getProfileImageUrl());
        MemberRecordDetailRes memberRecordDetails = new MemberRecordDetailRes(memberRecordService.getMemberRecord(member.getNickname()));

        return Pair.of(memberInfoDetails, memberRecordDetails);
    }


    @Override
    public MemberProfileUpdateDetailsRes updateMemberProfile(HttpServletRequest request, MemberUpdateReq memberUpdateReq, MultipartFile profileImage, Boolean deleteProfileImage, String username) {

        Boolean nicknameChanged = false;
        String token = jwtUtil.resolveAccessToken(request);
        String nickname = jwtUtil.getNickname(token);
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(MemberNotFoundException::new);

        // 요청한 유저와 토큰의 주인이 같은지 검증
        if (!username.equals(member.getUsername())) {
            throw new UnauthorizedAccessException();
        }

        // 변경할 닉네임이 있을 경우에만 업데이트
        if (memberUpdateReq.nickname() != null && !memberUpdateReq.nickname().isEmpty()) {
            // 현재 닉네임과 동일한지 검증
            if (memberUpdateReq.nickname().equals(member.getNickname())) {
                throw new NicknameNotChangedException();
            }

            boolean nicknameExists = memberRepository.existsByNickname(memberUpdateReq.nickname());
            // 이미 존재하는 닉네임인지 검증
            if (nicknameExists) {
                throw new DuplicateNicknameException();
            }
            member.updateNickname(memberUpdateReq.nickname());
            nicknameChanged = true;
        }

        if (deleteProfileImage) {
            member.updateProfileImageUrl("https://handtris.s3.ap-northeast-2.amazonaws.com/profile/defaultImage.png");
        } else if (profileImage != null && profileImage.getSize() > 0) {
        // 이미지가 존재하는 경우에만 업데이트
            validateImage(profileImage);
            uploadImage(profileImage, member);
        }

        String accessToken = nicknameChanged ? jwtUtil.createAccessToken(member.getNickname()) : null;

        return new MemberProfileUpdateDetailsRes(member.getNickname(), member.getProfileImageUrl(), accessToken);
    }

    private void validateImage(MultipartFile profileImage) {
        // 이미지 형식 검증 추가
        String contentType = profileImage.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidImageTypeException();
        }
        // 허용된 확장자 검증 추가
        String originalFilename = profileImage.getOriginalFilename();
        if (originalFilename == null) {
            throw new InvalidImageTypeException();
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new InvalidImageTypeException();
        }
    }

    private void uploadImage(MultipartFile profileImage, Member member) {
        try {
            String uploadedImageUrl = s3UploaderService.upload(profileImage, "profile");
            member.updateProfileImageUrl(uploadedImageUrl);
        } catch (IOException e) {
            throw new ImageUploadException();
        }
    }
}
