package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.Member;

public record MemberDetailRes(
        String username,
        String nickname
) {
    public MemberDetailRes(Member member) {
        this(member.getUsername(), member.getNickname());
    }
}
