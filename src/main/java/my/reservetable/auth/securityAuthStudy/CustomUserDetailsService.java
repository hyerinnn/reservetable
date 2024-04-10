package my.reservetable.auth.securityAuthStudy;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.Member;
import my.reservetable.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException("No user found with email " + email);
        }
        //List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole()));

        return null;
    }
}
