package my.reservetable.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.member.domain.Member;
import my.reservetable.member.dto.LoginMemberDetails;
import my.reservetable.member.dto.MemberDto;
import my.reservetable.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                // 시큐리티 과정 중에 발생한 exception은 controllerAdvice까지 연결되지 못해서, 아래 오류로 처리해야함
                () -> new InternalAuthenticationServiceException("인증실패")
        );

        //TODO : 왜 ModelMapper썼는지
        ModelMapper mapper = new ModelMapper();
        MemberDto memberDto = mapper.map(member, MemberDto.class);
        return new LoginMemberDetails(memberDto);
    }
}
