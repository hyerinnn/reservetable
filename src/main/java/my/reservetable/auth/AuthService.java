package my.reservetable.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.auth.signup.SignupRequest;
import my.reservetable.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request){
        log.info("========================================");
        log.info("email = {}", request.getEmail());
        log.info("password = {}", request.getPassword());

        return "로그인 성공";
    }

}
