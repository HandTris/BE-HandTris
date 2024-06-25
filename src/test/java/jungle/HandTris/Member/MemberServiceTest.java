package jungle.HandTris.Member;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jungle.HandTris.application.service.MemberService;
import jungle.HandTris.domain.Member;
import jungle.HandTris.domain.exception.DuplicateNicknameException;
import jungle.HandTris.domain.exception.DuplicateUsernameException;
import jungle.HandTris.domain.exception.PasswordMismatchException;
import jungle.HandTris.domain.exception.UserNotFoundException;
import jungle.HandTris.domain.repo.MemberRepository;
import jungle.HandTris.presentation.dto.request.MemberRequest;
import jungle.HandTris.presentation.dto.response.MemberDetailRes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private Validator validator;

    @Nested
    @DisplayName("회원 가입")
    class Signup {

        @Test
        @DisplayName("정상 회원가입")
        public void testSignupSuccess() {
            // given
            MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "user1");

            // when
            memberService.signup(member);

            // then
            Member findMember = memberRepository.findByUsername(member.username());

        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.username());
        Assertions.assertThat(bCryptPasswordEncoder.matches(member.password(), findMember.getPassword())).isTrue();
        Assertions.assertThat(findMember.getNickname()).isEqualTo(member.nickname());

        }

        @Nested
        @DisplayName("Username 관련 예외 처리")
        class Username {

            @Test
            @DisplayName("중복 Username")
            public void testSignupFailWithDuplicateUsername() {
            // given
            MemberRequest member1 = new MemberRequest("user1", "1q2w3e4r!", "user1");
            MemberRequest member2 = new MemberRequest("user1", "1q2w3e4r!", "user2");
            memberService.signup(member1);

            // when & then

            Assertions.assertThatThrownBy(() -> memberService.signup(member2))
                    .isInstanceOf(DuplicateUsernameException.class);
            }

            @Test
            @DisplayName("3 자리 이하 Username")
            public void testSignupFailWithTooShortUsername() {
                // given
                MemberRequest member = new MemberRequest("use", "1q2w3e4r!", "user1");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("아이디는 최소 4자 이상, 10자 이하여야 합니다");
            }

            @Test
            @DisplayName("11 자리 이상 Username")
            public void testSignupFailWithTooLongUsername() {
                // given
                MemberRequest member = new MemberRequest("longlonglonguser", "1q2w3e4r!", "user1");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("아이디는 최소 4자 이상, 10자 이하여야 합니다");
            }

            @Test
            @DisplayName("알파벳 대문자가 포함된 Username")
            public void testSignupFailWithUppercaseUsername() {
                // given
                MemberRequest member = new MemberRequest("User1", "1q2w3e4r!", "user1");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("아이디는 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
            }

            @Test
            @DisplayName("특수 문자가 포함된 Username")
            public void testSignupFailWithSpecialCharacterUsername() {
                // given
                MemberRequest member = new MemberRequest("user1!", "1q2w3e4r!", "user1");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("아이디는 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
            }

            @Test
            @DisplayName("`admin` 작성된 Username")
            public void testSignupFailWithAdminUsername() {
                // given
                MemberRequest member = new MemberRequest("admin", "1q2w3e4r!", "user1");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("admin은 사용할 수 없습니다.");
            }

        }

        @Nested
        @DisplayName("Nickname 관련 예외 처리")
        class Nickname {

            @Test
            @DisplayName("중복 Nickname")
            public void testSignupFailWithDuplicateNickname() {
                // given
                MemberRequest member1 = new MemberRequest("user1", "1q2w3e4r!", "user1");
                MemberRequest member2 = new MemberRequest("user2", "1q2w3e4r!", "user1");
                memberService.signup(member1);

                // when & then

                Assertions.assertThatThrownBy(() -> memberService.signup(member2))
                        .isInstanceOf(DuplicateNicknameException.class);

            }

            @Test
            @DisplayName("3 자리 이하 Nickname")
            public void testSignupFailWithTooShortNickname() {
                // given
                MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "use");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("닉네임은 최소 4자 이상, 20자 이하여야 합니다");
            }

            @Test
            @DisplayName("21 자리 이상 Nickname")
            public void testSignupFailWithTooLongNickname() {
                // given
                MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "longlonglonglonglonguser");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("닉네임은 최소 4자 이상, 20자 이하여야 합니다");
            }

            @Test
            @DisplayName("특수 문자가 포함된 Nickname")
            public void testSignupFailWithSpecialCharacterNickname() {
                // given
                MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "user1^^");

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("닉네임은 알파벳 대소문자와 숫자만 사용 가능합니다.");
            }

            @ParameterizedTest
            @ValueSource(strings ={"admin", "administator", "useradmin", "adminuser"})
            @DisplayName("admin 작성된 Nickname")
            public void testSignupFailWithAdminNickname(String nickname) {
                // given
                MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", nickname);

                // when
                Set<ConstraintViolation<MemberRequest>> violations = validator.validate(member);

                // then
                Assertions.assertThat(violations).isNotEmpty();
                Assertions.assertThat(violations.stream().findFirst().get().getMessage()).isEqualTo("admin은 사용할 수 없습니다.");
            }

        }
    }

    @Nested
    @DisplayName("로그인")
    class Signin {

        @Test
        @DisplayName("정상 로그인")
        public void testLoginSuccess() {
            // given
            MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "user1");
            memberService.signup(member);

            // when
            Pair<MemberDetailRes, String> result = memberService.signin(member);

            // then
            Assertions.assertThat(result).isNotNull();

            Assertions.assertThat(result.getFirst()).isNotNull(); // Member 객체 확인
            Assertions.assertThat(result.getSecond()).isNotBlank(); // Access Token 확인

            Assertions.assertThat(result.getFirst().username()).isEqualTo("user1");
            Assertions.assertThat(result.getFirst().nickname()).isEqualTo("user1");

        }

        @Test
        @DisplayName("존재하지 않는 Username으로 접근")
        public void testLoginFailWithNonexistentUsername() {
            // given
            MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "user1");

            // when & then
            Assertions.assertThatThrownBy(() -> memberService.signin(member))
                    .isInstanceOf(UserNotFoundException.class);

        }

        @Test
        @DisplayName("일치하지 않은 Password 입력")
        public void testLoginFailWithIncorrectPassword() {
            // given
            MemberRequest member = new MemberRequest("user1", "1q2w3e4r!", "user1");
            memberService.signup(member);


            // when
            MemberRequest incorrectMember = new MemberRequest("user1", "wrongPassword", "user1");
            Assertions.assertThatThrownBy(() -> memberService.signin(incorrectMember))
                    .isInstanceOf(PasswordMismatchException.class);
            // then

        }
    }

}