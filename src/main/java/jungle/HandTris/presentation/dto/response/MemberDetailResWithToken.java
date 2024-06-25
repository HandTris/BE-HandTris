package jungle.HandTris.presentation.dto.response;

public record MemberDetailResWithToken (String username,
                                        String nickname,
                                        String accessToken,
                                        String refreshToken) {

}
