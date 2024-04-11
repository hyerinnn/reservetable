package my.reservetable.auth.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.config.jwt.JwtTokenProvider;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.MemberRepository;
import my.reservetable.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    //private final ModelMapper modelMapper;

    public String login(LoginRequest request){
        log.info("========================================");
        log.info("email = {}", request.getEmail());
        log.info("password = {}", request.getPassword());

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotExistMemberException("회원을 찾을 수 없습니다."));


        return "로그인 성공";
    }
}
