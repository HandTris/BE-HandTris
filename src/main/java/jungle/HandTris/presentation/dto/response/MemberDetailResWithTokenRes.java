package jungle.HandTris.presentation.dto.response;

public record MemberDetailResWithTokenRes(String username,
                                          String nickname,
                                          String accessToken,
                                          String refreshToken) {

}
