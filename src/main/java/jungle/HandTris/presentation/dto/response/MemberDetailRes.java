package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.Member;
import lombok.Getter;

@Getter
public class MemberDetailRes {
    private final String username;
    private final String password;
    private final String nickname;

    public MemberDetailRes(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
