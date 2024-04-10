package my.reservetable.auth.signup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(SignupRequest request){

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        log.info("passwordEncode = {}", encryptedPassword);

        memberRepository.save(request.toEntity(encryptedPassword));
        return "가입 성공";
    }

}
