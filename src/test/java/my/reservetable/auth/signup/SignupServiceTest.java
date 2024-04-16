package my.reservetable.auth.signup;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.member.MemberResponse;
import my.reservetable.member.domain.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

class SignupServiceTest extends IntegrationTestSupport {

    @Autowired SignupService signupService;
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @DisplayName("비밀번호는 암호화돼서 저장된다.")
    @Test
    void passwordEncodeTest() {
        // given
        String password = "1234";

        // when
        String encodedPassword = passwordEncoder.encode(password);

        // then
        assertThat(encodedPassword).isNotEqualTo(password);
    }

    @DisplayName("사장님 회원가입 시, OWNER 권한으로 회원가입에 성공한다.")
    @Test
    void signupOwnerSuccess() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("owner@owner.com")
                .password("1234")
                .nickName("새로운회원")
                .phoneNumber("01012345678")
                .build();

        // when
        MemberResponse ownerMember = signupService.signupOwner(request);

        // then
        assertThat(ownerMember.getId()).isNotNull();
        assertThat(ownerMember.getRole()).isEqualTo(MemberRole.OWNER);
    }

    @DisplayName("일반사용자 회원가입 시, MEMBER 권한으로 회원가입에 성공한다.")
    @Test
    void signupMemberSuccess() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("member@member.com")
                .password("1234")
                .nickName("새로운회원")
                .phoneNumber("01012345678")
                .build();

        // when
        MemberResponse member = signupService.signupMember(request);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getRole()).isEqualTo(MemberRole.MEMBER);
    }
}