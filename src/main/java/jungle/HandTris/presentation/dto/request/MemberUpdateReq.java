package jungle.HandTris.presentation.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateReq (@Size(min = 4, max = 20, message = "닉네임은 최소 4자 이상, 20자 이하여야 합니다")
                               @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "닉네임은 알파벳 대소문자와 숫자만 사용 가능합니다.")
                               @Pattern(regexp = "^((?!admin).)*$", message = "admin은 사용할 수 없습니다.")
                               String nickname) {
}
