package my.reservetable.point.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.member.domain.Member;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.dto.SignupRequest;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.service.SignupService;
import my.reservetable.point.domain.PointType;
import my.reservetable.point.repository.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointServiceTest extends IntegrationTestSupport {
    @Autowired MemberRepository memberRepository;
    @Autowired SignupService signupService;
    @Autowired PointService pointService;
    @Autowired PointRepository pointRepository;

    @DisplayName("포인트 조회")
    @Test
    void getPoint() {
        // given
        Member user = createMember("user@user.com");
        memberRepository.save(user);

        // when
        int myPoint = pointService.getPoint(user.getId());

        // then
        assertThat(myPoint).isEqualTo(0);
    }

    @DisplayName("회원가입 시, 회원가입 포인트를 적립한다.")
    @Test
    void addPointForSignUp() {
        // given
        SignupRequest request = SignupRequest.builder()
                .nickName("유저")
                .password("1111")
                .email("user@user.com")
                .phoneNumber("01027374848")
                .build();
        MemberResponse user = signupService.signupUser(request);

        // when
        int myPoint = pointService.getPoint(user.getId());

        // then
        assertThat(myPoint).isEqualTo(PointType.SIGNUP.getPoint());
    }

    @DisplayName("회원가입 이후에는 일반 포인트가 적립된다.")
    @Test
    void addPoint() {
        // given
        SignupRequest request = SignupRequest.builder()
                .nickName("유저")
                .password("1111")
                .email("user@user.com")
                .phoneNumber("01027374848")
                .build();
        MemberResponse user = signupService.signupUser(request);

        // when
        pointService.addPoint(user.getId());
        int myPoint = pointService.getPoint(user.getId());

        // then
        assertThat(myPoint).isEqualTo(PointType.SIGNUP.getPoint() + PointType.GENERAL.getPoint());
    }

    @DisplayName("포인트를 성공적으로 차감한다.")
    @Test
    void subtractPoint() {
        // given
        SignupRequest request = SignupRequest.builder()
                .nickName("유저")
                .password("1111")
                .email("user@user.com")
                .phoneNumber("01027374848")
                .build();
        MemberResponse user = signupService.signupUser(request);

        // when
        int myPoint = pointService.subtractPoint(user.getId(), PointType.GENERAL.getPoint());

        // then
        assertThat(myPoint).isEqualTo(40);
    }

    @DisplayName("포인트가 부족한 경우 에러가 발생한다.")
    @Test
    void subtractPointFailed() {
        // given
        SignupRequest request = SignupRequest.builder()
                .nickName("유저")
                .password("1111")
                .email("user@user.com")
                .phoneNumber("01027374848")
                .build();
        MemberResponse user = signupService.signupUser(request);

        // when & then
        assertThatThrownBy(() -> pointService.subtractPoint(user.getId(), 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포인트가 부족합니다.");
    }

    private Member createMember(String email){
        return Member.builder()
                .nickName("유저")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .build();
    }
}