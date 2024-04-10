package my.reservetable.auth.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public String login(LoginRequest request){
        log.info("========================================");
        log.info("email = {}", request.getEmail());
        log.info("password = {}", request.getPassword());

        return "로그인 성공";
    }

}
