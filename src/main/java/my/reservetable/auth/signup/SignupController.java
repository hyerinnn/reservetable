package my.reservetable.auth.signup;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.MemberResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup/main")
    public String signUpPage(){
        return "회원가입 페이지 입니다.";
    }

    @PostMapping("/signup/owner")
    public MemberResponse signUpOwner(@Valid @RequestBody SignupRequest request){
        return signupService.signupOwner(request);
    }

    @PostMapping("/signup/member")
    public MemberResponse signUpMember(@Valid @RequestBody SignupRequest request){
        return signupService.signupMember(request);
    }

    //todo : 패스워드 변경

}
