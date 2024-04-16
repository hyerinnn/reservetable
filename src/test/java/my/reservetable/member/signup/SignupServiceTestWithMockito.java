package my.reservetable.member.signup;

import my.reservetable.config.dummy.DummyObject;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.dto.SignupRequest;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.member.service.SignupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class SignupServiceTestWithMockito extends DummyObject {

    @InjectMocks
    private SignupService signupService;
    @Mock //가짜 객체 주입
    private MemberRepository memberRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

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

        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        Member newMember = newMockMember(1L, "owner@owner.com", MemberRole.OWNER);
        when(memberRepository.save(any())).thenReturn(newMember);

        // when
        MemberResponse ownerMember = signupService.signupOwner(request);

        // then
        assertThat(ownerMember.getId()).isEqualTo(1L);
        assertThat(ownerMember.getRole()).isEqualTo(MemberRole.OWNER);
    }

    @DisplayName("일반사용자 회원가입 시, USER 권한으로 회원가입에 성공한다.")
    @Test
    void signupUserSuccess() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("user@user.com")
                .password("1234")
                .nickName("새로운회원")
                .phoneNumber("01012345678")
                .build();

        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        Member newMember = newMockMember(1L, "user@user.com", MemberRole.USER);
        when(memberRepository.save(any())).thenReturn(newMember);

        // when
        MemberResponse ownerMember = signupService.signupOwner(request);

        // then
        assertThat(ownerMember.getId()).isEqualTo(1L);
        assertThat(ownerMember.getRole()).isEqualTo(MemberRole.USER);
    }
}