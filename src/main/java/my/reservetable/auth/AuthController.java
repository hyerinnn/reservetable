package my.reservetable.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/main")
    public String loginPage(){
        return "로그인이 필요합니다.";
    }

    @PostMapping("/login")
    public String login(LoginRequest request){
        return authService.login(request);
    }

    @GetMapping("/signup/main")
    public String signUpPage(){
        return "회원가입 페이지 입니다.";
    }

/*    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody SignupRequest request){
        return authService.signup(request);
    }*/
}
