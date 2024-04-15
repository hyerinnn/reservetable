package my.reservetable.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.MemberRepository;
import my.reservetable.member.domain.Member;
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
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;


    // 시큐리티로 로그인이 될 떄, username을 체크하고, 있으면 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 생성된다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
               // () -> new InternalAuthenticationServiceException("인증실패")
            () -> new NotExistMemberException("해당 회원을 찾을 수 없습니다.")

        );
        //List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().name()));
        ModelMapper mapper = new ModelMapper();
        MemberDto memberDto = mapper.map(member, MemberDto.class);

        return new MemberInfo(memberDto);

    }

}
