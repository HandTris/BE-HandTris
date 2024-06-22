package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.Member;

public record MemberDetailRes(
        String username,
        String password,
        String nickname
) {
    public MemberDetailRes(Member member) {
        this(member.getUsername(), member.getPassword(), member.getNickname());
    }
}
