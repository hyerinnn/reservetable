package my.reservetable.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.AccountRequest;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody AccountRequest request){
        return authService.signup(request);
    }
}
