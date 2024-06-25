package jungle.HandTris.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberRequest(@NotBlank(message = "아이디는 필수 입력 값입니다.")
                            @Size(min = 4, max = 10, message = "아이디는 최소 4자 이상, 10자 이하여야 합니다")
                            @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
                            @Pattern(regexp = "^((?!admin).)*$", message = "admin은 사용할 수 없습니다.")
                            String username,

                            @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
                            @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하여야 합니다.")
                            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]+$", message = "비밀번호는 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.")
                            String password,

                            @NotBlank(message = "닉네임은 필수 입력 값입니다.")
                            @Size(min = 4, max = 20, message = "닉네임은 최소 4자 이상, 20자 이하여야 합니다")
                            @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "닉네임은 알파벳 대소문자와 숫자만 사용 가능합니다.")
                            @Pattern(regexp = "^((?!admin).)*$", message = "admin은 사용할 수 없습니다.")
                            String nickname) {

}

