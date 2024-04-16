package my.reservetable.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.config.jwt.JwtTokenProvider;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.dto.LoginMemberDetails;
import my.reservetable.member.dto.LoginRequest;
import my.reservetable.member.dto.MemberDto;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 시큐리티로 로그인이 될 떄, username을 체크하고, 있으면 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 생성된다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
               // () -> new InternalAuthenticationServiceException("인증실패")
            () -> new NotExistMemberException("존재하지 않는 회원입니다.")

        );
        //List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().name()));
        ModelMapper mapper = new ModelMapper();
        MemberDto memberDto = mapper.map(member, MemberDto.class);

        return new LoginMemberDetails(memberDto);
    }

    public String createToken(LoginRequest request){
        MemberResponse member =  memberRepository.findByEmail(request.getEmail())
                .map(m -> MemberResponse.toDto(m))
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));
        return jwtTokenProvider.createJwtToken(member);
    }

}
