package my.reservetable.auth.signup;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.MemberResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public MemberResponse signUp(@Valid @RequestBody SignupRequest request){
        return signupService.signup(request);
    }

    //todo : 패스워드 변경

}
