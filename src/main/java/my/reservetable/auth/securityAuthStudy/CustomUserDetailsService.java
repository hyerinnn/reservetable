package my.reservetable.auth.securityAuthStudy;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.MemberRepository;
import my.reservetable.member.domain.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByEmail(email);
        if(!member.isPresent()){
            throw new UsernameNotFoundException("No user found with email " + email);
        }
        //List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole()));

        return null;
    }
}
